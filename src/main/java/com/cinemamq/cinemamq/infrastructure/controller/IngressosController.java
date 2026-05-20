package com.cinemamq.cinemamq.infrastructure.controller;

import com.cinemamq.cinemamq.infrastructure.config.RabbitMQConfig;
import com.cinemamq.cinemamq.infrastructure.model.dto.CompraIngressoDTO;
import com.cinemamq.cinemamq.infrastructure.model.dto.PedidoFilaDTO;
import com.cinemamq.cinemamq.infrastructure.model.dto.RespostaPedidoDTO;
import com.cinemamq.cinemamq.infrastructure.model.entity.CompraEntity;
import com.cinemamq.cinemamq.infrastructure.repository.CompraRepository;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/ingressos")
public class IngressosController {

	@Autowired
	private RabbitTemplate rabbitTemplate;
	@Autowired
	private CompraRepository compraRepository;

	@PostMapping("/comprar")
	public ResponseEntity<RespostaPedidoDTO> comprarIngresso(@RequestBody CompraIngressoDTO dto){
		UUID pedidoId = UUID.randomUUID();

		CompraEntity compraInicial = new CompraEntity();
		compraInicial.setId(pedidoId);
		compraInicial.setNomeComprador(dto.nomeComprador());
		compraInicial.setStatus("PROCESSANDO");

		compraRepository.save(compraInicial);

		PedidoFilaDTO envelop = new PedidoFilaDTO(pedidoId, dto);
		rabbitTemplate.convertAndSend(
						RabbitMQConfig.EXCHANGE_INGRESSOS,
						RabbitMQConfig.ROUTING_KEY_INGRESSOS,
						envelop
		);


		return ResponseEntity.accepted().body(
						new RespostaPedidoDTO(pedidoId, "Pedido recebido! Processando sua reserva..."));
	}

	@GetMapping("/status/{id}")
	public ResponseEntity<?> consultarStatus(@PathVariable UUID id) {
		CompraEntity compra = compraRepository.findById(id).orElse(null);

		if (compra == null) {
			return ResponseEntity.notFound().build();
		}

		// De acordo com o status atualizado pelo Consumer, respondemos o front-end
		return switch (compra.getStatus()) {
			case "PROCESSANDO" -> ResponseEntity.ok().body(compra.getStatus());
			case "SUCESSO" -> ResponseEntity.ok(compra); // Devolve o comprovante completo
			case "ESGOTADO" -> ResponseEntity.badRequest().body(compra.getStatus() + "\"O assento selecionado já foi ocupado por outro cliente.\"");
			default -> ResponseEntity.internalServerError().build();
		};
	}
}
