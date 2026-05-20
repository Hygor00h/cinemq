package com.cinemamq.cinemamq.infrastructure.config.consumer;

import com.cinemamq.cinemamq.infrastructure.config.RabbitMQConfig;
import com.cinemamq.cinemamq.infrastructure.model.dto.CompraIngressoDTO;
import com.cinemamq.cinemamq.infrastructure.model.dto.PedidoFilaDTO;
import com.cinemamq.cinemamq.infrastructure.model.entity.AssentoEntity;
import com.cinemamq.cinemamq.infrastructure.model.entity.CompraEntity;
import com.cinemamq.cinemamq.infrastructure.model.entity.FilmeEntity;
import com.cinemamq.cinemamq.infrastructure.repository.AssentoRepository;
import com.cinemamq.cinemamq.infrastructure.repository.CompraRepository;
import com.cinemamq.cinemamq.infrastructure.repository.FilmeRepository;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class IngressosConsumer {

	@Autowired
	private CompraRepository compraRepository;

	@Autowired
	private AssentoRepository assentoRepository;

	@Autowired
	private FilmeRepository filmeRepository;

	@RabbitListener(queues = RabbitMQConfig.FILA_INGRESSOS)
	public void processarPedido(PedidoFilaDTO envelope) {
		CompraEntity compraDoBanco = compraRepository.findById(envelope.pedidoId()).orElse(null);
		if (compraDoBanco == null) return;

		AssentoEntity assento = assentoRepository.findById(envelope.dados().assentoId()).orElse(null);

		// Lógica de Concorrência
		if (assento != null && !assento.isOcupado()) {
			// Ocupa o assento
			assento.setOcupado(true);
			assentoRepository.save(assento);

			// Atualiza a compra existente para SUCESSO
			compraDoBanco.setStatus("SUCESSO");
			compraDoBanco.setNumeroAssento(assento.getNumero());
			compraRepository.save(compraDoBanco);
			System.out.println(" Pedido " + envelope.pedidoId() + " processado com SUCESSO!");
		} else {
			// Atualiza a compra existente para ESGOTADO
			compraDoBanco.setStatus("ESGOTADO");
			compraDoBanco.setMensagemErro("O assento selecionado já foi ocupado por outro cliente.");
			compraRepository.save(compraDoBanco);
			System.out.println(" Pedido " + envelope.pedidoId() + " ESGOTOU!");
		}
	}


//	@RabbitListener(queues = RabbitMQConfig.FILA_INGRESSOS)
//	public void processarPedido(CompraIngressoDTO dto) {
//		System.out.println("\n[RabbitMQ] Retirando pedido da fila para processamento...");
//
//		AssentoEntity assento = assentoRepository.findById(dto.assentoId()).orElse(null);
//		FilmeEntity filme = filmeRepository.findById(dto.filmeId()).orElse(null);
//
//		if (assento == null || filme == null) {
//			System.out.println(" ERRO CRÍTICO: Filme ou Assento inválido.");
//			return;
//		}
//
//		if (!assento.isOcupado()) {
//
//			assento.setOcupado(true);
//			assentoRepository.save(assento);
//
//			CompraEntity compra = new CompraEntity(
//							dto.nomeComprador(),
//							dto.horario(),
//							filme,
//							assento,
//							filme.getValorIngresso()
//			);
//			compraRepository.save(compra);
//
//			// EMITE O COMPROVANTE (Sucesso)
//			System.out.println("==================================================");
//			System.out.println(" COMPROVANTE DE PAGAMENTO EMITIDO");
//			System.out.println("Cliente: " + dto.nomeComprador());
//			System.out.println("Filme: " + filme.getNome() + " | Sessão: " + dto.horario());
//			System.out.println("Número do Assento Reservado: " + assento.getNumero());
//			System.out.println("Valor Pago: R$ " + filme.getValorIngresso());
//			System.out.println("Status: SUCESSO - Compra Concluída!");
//			System.out.println("==================================================");
//
//		} else {
//			// NOTIFICAÇÃO DE ESGOTADO (A terceira pessoa cai aqui)
//			System.out.println("==================================================");
//			System.out.println(" NOTIFICAÇÃO DE ASSENTO INDISPONÍVEL");
//			System.out.println("Atenção, " + dto.nomeComprador() + "!");
//			System.out.println("O assento número " + assento.getNumero() + " para o filme " + filme.getNome() + " ACABOU DE ESGOTAR.");
//			System.out.println("Por favor, selecione outro assento ou horário.");
//			System.out.println("==================================================");
//		}
//	}
}
