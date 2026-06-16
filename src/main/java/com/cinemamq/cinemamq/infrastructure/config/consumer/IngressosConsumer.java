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

import java.util.UUID;

@Component
public class IngressosConsumer {

	@Autowired
	private CompraRepository compraRepository;

	@Autowired
	private AssentoRepository assentoRepository;

	@Autowired
	private FilmeRepository filmeRepository;

	@RabbitListener(queues = RabbitMQConfig.FILA_INGRESSOS)
	@Transactional
	public void processarCompra(PedidoFilaDTO pedido) {
		try {
			CompraEntity compra = compraRepository.findById(pedido.getPedidoId())
							.orElseThrow(() -> new RuntimeException("Compra não encontrada"));

			AssentoEntity assento = assentoRepository.findById(pedido.getDados().getId())
							.orElseThrow(() -> new RuntimeException("Assento não encontrado"));

			UUID salaDoAssentoId = assento.getSala().getId();
			UUID filmeDaSalaId = assento.getSala().getFilme().getId();

			if (!salaDoAssentoId.equals(pedido.getDados().getSala()) || !filmeDaSalaId.equals(pedido.getDados().getFilmeId())) {
				compra.setStatus("ERRO_VALIDACAO");
				compra.setMensagemErro("O assento selecionado não pertence à sala ou ao filme informado.");
				compraRepository.save(compra);
				return;
			}

			if (assento.getOcupado()) {
				compra.setStatus("ESGOTADO");
				compra.setMensagemErro("O assento selecionado já foi ocupado por outro cliente.");
				compraRepository.save(compra);
				return;
			}

			assento.setOcupado(true);
			assentoRepository.save(assento);

			compra.setStatus("SUCESSO");
			compraRepository.save(compra);

			System.out.println("Pedido " + pedido.getPedidoId() + " processado com sucesso!");

		} catch (Exception e) {
			System.err.println("Erro ao processar a compra: " + e.getMessage());
		}
	}
}
