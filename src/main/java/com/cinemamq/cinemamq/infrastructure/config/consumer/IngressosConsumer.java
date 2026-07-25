package com.cinemamq.cinemamq.infrastructure.config.consumer;

import com.cinemamq.cinemamq.infrastructure.config.RabbitMQConfig;
import com.cinemamq.cinemamq.infrastructure.model.dto.PedidoFilaDTO;
import com.cinemamq.cinemamq.infrastructure.model.entity.AssentoEntity;
import com.cinemamq.cinemamq.infrastructure.model.entity.CompraEntity;
import com.cinemamq.cinemamq.infrastructure.repository.AssentoRepository;
import com.cinemamq.cinemamq.infrastructure.repository.CompraRepository;
import com.cinemamq.cinemamq.infrastructure.repository.FilmeRepository;
import jakarta.transaction.Transactional;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
public class IngressosConsumer {

	@Autowired
	private CompraRepository compraRepository;

	@Autowired
	private AssentoRepository assentoRepository;

	@RabbitListener(queues = RabbitMQConfig.FILA_INGRESSOS)
	@Transactional
	public void processarCompra(PedidoFilaDTO pedido) {
		try {
			CompraEntity compra = compraRepository.findById(pedido.getPedidoId())
							.orElseThrow(() -> new RuntimeException("Compra não encontrada"));

			List<UUID> assentosIds = pedido.getDados().getAssentosIds();
			List<AssentoEntity> assentos = assentoRepository.findAllById(assentosIds);

			if (assentos.isEmpty() || assentos.size() != assentosIds.size()) {
				compra.setStatus("ERRO_VALIDACAO");
				compra.setMensagemErro("Um ou mais assentos selecionados não foram encontrados.");
				compraRepository.save(compra);
				return;
			}

			for (AssentoEntity assento : assentos) {
				UUID salaDoAssentoId = assento.getSala().getId();
				UUID filmeDaSalaId = assento.getSala().getFilme().getId();

				if (!salaDoAssentoId.equals(pedido.getDados().getSala()) || !filmeDaSalaId.equals(pedido.getDados().getFilmeId())) {
					compra.setStatus("ERRO_VALIDACAO");
					compra.setMensagemErro("Um ou mais assentos selecionados não pertencem à sala ou ao filme informado.");
					compraRepository.save(compra);
					return;
				}
			}

			boolean algumOcupado = assentos.stream().anyMatch(AssentoEntity::getOcupado);

			if (algumOcupado) {
				compra.setStatus("ESGOTADO");
				compra.setMensagemErro("Um ou mais assentos selecionados já foram ocupados por outro cliente.");
				compraRepository.save(compra);
				return;
			}

			// Marca os assentos como ocupados/reservados
			for (AssentoEntity assento : assentos) {
				assento.setOcupado(true);
			}
			assentoRepository.saveAll(assentos);

			// 💡 ALTERAÇÃO AQUI: Em vez de SUCESSO, fica aguardando o pagamento
			compra.setStatus("AGUARDANDO_PAGAMENTO");
			compraRepository.save(compra);

			System.out.println("Reserva concluída! Pedido " + pedido.getPedidoId() + " aguardando pagamento.");

		} catch (Exception e) {
			System.err.println("Erro ao processar a compra na fila: " + e.getMessage());
		}
	}
}