package com.cinemamq.cinemamq.infrastructure.config.consumer;

import com.cinemamq.cinemamq.infrastructure.config.RabbitMQConfig;
import com.cinemamq.cinemamq.infrastructure.model.dto.CompraIngressoDTO;
import com.cinemamq.cinemamq.infrastructure.model.dto.PedidoFilaDTO;
import com.cinemamq.cinemamq.infrastructure.model.entity.AssentoEntity;
import com.cinemamq.cinemamq.infrastructure.model.entity.CompraEntity;
import com.cinemamq.cinemamq.infrastructure.repository.AssentoRepository;
import com.cinemamq.cinemamq.infrastructure.repository.CompraRepository;
import com.cinemamq.cinemamq.infrastructure.repository.FilmeRepository;
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

//	@RabbitListener(queues = RabbitMQConfig.FILA_INGRESSOS)
//	public void processarPedido(PedidoFilaDTO envelope) {
//		CompraEntity compraDoBanco = compraRepository.findById(envelope.pedidoId()).orElse(null);
//		if (compraDoBanco == null) return;
//
//		AssentoEntity assento = assentoRepository.findById(envelope.dados().id()).orElse(null);
//
//		// Lógica de Concorrência
//		if (assento != null && !assento.isOcupado()) {
//			// Ocupa o assento
//			assento.setOcupado(true);
//			assentoRepository.save(assento);
//
//			// Atualiza a compra existente para SUCESSO
//			compraDoBanco.setStatus("SUCESSO");
//			compraDoBanco.setAssento(assento);
//			compraRepository.save(compraDoBanco);
//			System.out.println(" Pedido " + envelope.pedidoId() + " processado com SUCESSO!");
//		} else {
//			// Atualiza a compra existente para ESGOTADO
//			compraDoBanco.setStatus("ESGOTADO");
//			compraDoBanco.setMensagemErro("O assento selecionado já foi ocupado por outro cliente.");
//			compraRepository.save(compraDoBanco);
//			System.out.println(" Pedido " + envelope.pedidoId() + " ESGOTOU!");
//		}
//	}

//2
	@RabbitListener(queues = RabbitMQConfig.FILA_INGRESSOS)
	public void processarCompra(PedidoFilaDTO pedido) {
		try {
			// 1. Busca o assento usando o ID dinâmico que veio do DTO
			AssentoEntity assento = assentoRepository.findById(pedido.dados().id())
							.orElse(null);

			// Validação 1: O assento existe?
			if (assento == null) {
				marcarComoErro(pedido.pedidoId(), "O assento selecionado não existe.");
				return;
			}

			// Validação 2: O assento pertence ao filme correto?
			if (!assento.getFilme().getId().equals(pedido.dados().filmeId())) {
				marcarComoErro(pedido.pedidoId(), "Este assento não pertence ao filme selecionado.");
				return;
			}

			// Validação 3: O assento já está ocupado? (A regra do primeiro a chegar)
			if (assento.isOcupado()) {
				marcarComoErro(pedido.pedidoId(), "O assento selecionado já foi ocupado por outro cliente.");
				System.out.println("Pedido " + pedido.pedidoId() + " ESGOTADO (Cadeira já ocupada).");
			} else {
				// SE ESTIVER LIVRE: Ganhador!
				assento.setOcupado(true);
				assentoRepository.save(assento); // Trava o assento no banco imediatamente

				salvarCompraSucesso(pedido.pedidoId(), pedido.dados(), assento);
				System.out.println("Pedido " + pedido.pedidoId() + " processado com SUCESSO!");
			}

		} catch (Exception e) {
			// Caso dê qualquer pane inesperada (banco caiu, etc), marca como erro para não travar a fila
			marcarComoErro(pedido.pedidoId(), "Erro interno ao processar a compra: " + e.getMessage());
		}
	}

	private void salvarCompraSucesso(UUID pedidoId, CompraIngressoDTO dto, AssentoEntity assento) {
		// 1. Busca a compra que o Controller criou com o status 'PROCESSANDO'
		CompraEntity compra = compraRepository.findById(pedidoId)
						.orElseThrow(() -> new RuntimeException("Pedido não encontrado no banco"));

		// 2. Atualiza os dados com o sucesso da reserva
		compra.setStatus("SUCESSO");
		compra.setAssento(assento); // Lembra da relação @ManyToOne que arrumamos?
		compra.setNomeComprador(dto.nomeComprador());
		compra.setHorario(dto.horario());

		// 3. Salva no banco. Agora quando o usuário der GET no status, verá "SUCESSO"
		compraRepository.save(compra);
	}

	private void marcarComoErro(UUID pedidoId, String motivoErro) {
		// 1. Busca a compra que o Controller criou
		CompraEntity compra = compraRepository.findById(pedidoId)
						.orElseThrow(() -> new RuntimeException("Pedido não encontrado no banco"));

		// 2. Atualiza o status para avisar o Frontend/Postman do que aconteceu
		compra.setStatus("ESGOTADO");
		compra.setMensagemErro(motivoErro); // Ex: "O assento já foi ocupado por outro cliente."

		// 3. Salva no banco
		compraRepository.save(compra);
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
