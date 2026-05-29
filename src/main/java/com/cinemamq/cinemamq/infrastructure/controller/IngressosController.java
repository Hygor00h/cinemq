package com.cinemamq.cinemamq.infrastructure.controller;

import com.cinemamq.cinemamq.infrastructure.config.RabbitMQConfig;
import com.cinemamq.cinemamq.infrastructure.model.dto.CompraIngressoDTO;
import com.cinemamq.cinemamq.infrastructure.model.dto.PedidoFilaDTO;
import com.cinemamq.cinemamq.infrastructure.model.dto.RespostaPedidoDTO;
import com.cinemamq.cinemamq.infrastructure.model.entity.AssentoEntity;
import com.cinemamq.cinemamq.infrastructure.model.entity.CompraEntity;
import com.cinemamq.cinemamq.infrastructure.model.entity.FilmeEntity;
import com.cinemamq.cinemamq.infrastructure.repository.AssentoRepository;
import com.cinemamq.cinemamq.infrastructure.repository.CompraRepository;
import com.cinemamq.cinemamq.infrastructure.repository.FilmeRepository;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/ingressos")
public class IngressosController {

	@Autowired
	private RabbitTemplate rabbitTemplate;

	@Autowired
	private CompraRepository compraRepository;

	@Autowired
	private AssentoRepository assentoRepository;

	@Autowired
	private FilmeRepository filmeRepository;


	@PostMapping("/comprar")
	public ResponseEntity<CompraEntity> comprar(@RequestBody CompraIngressoDTO dto) {
		// 1. Gera o ID do pedido imediatamente
		UUID pedidoId = UUID.randomUUID();

		// 2. Cria a compra no banco com status PROCESSANDO
		CompraEntity compra = new CompraEntity();
		compra.setId(pedidoId);
		compra.setStatus("SUCESSO");
		compra.setHorario(dto.horario());
		compra.setNomeComprador(dto.nomeComprador());

		AssentoEntity assento = assentoRepository.findById(dto.id()).orElseThrow(()->new RuntimeException("Assento não encontrado"));
		compra.setAssento(assento);

		Optional<FilmeEntity> filmeid = filmeRepository.findById(dto.filmeId());
		compra.setFilme(filmeid.get());

		compraRepository.save(compra);

		// 3. Monta o pacote para a fila com o ID e os dados do Postman
		PedidoFilaDTO pedidoFila = new PedidoFilaDTO(pedidoId, dto);

		// 4. Joga na fila do RabbitMQ de forma assíncrona
		rabbitTemplate.convertAndSend(RabbitMQConfig.ROUTING_KEY_INGRESSOS, RabbitMQConfig.ROUTING_KEY_INGRESSOS, pedidoFila);

		// 5. Responde na hora para o cliente: "Recebi seu pedido, acompanhe pelo ID tal"
		return ResponseEntity.accepted().body(compra);
	}


//1
//	@PostMapping("/comprar")
//	public ResponseEntity<RespostaPedidoDTO> comprarIngresso(@RequestBody CompraIngressoDTO dto){
//		UUID pedidoId = UUID.randomUUID();
//
//		CompraEntity compraInicial = new CompraEntity();
//		compraInicial.setId(pedidoId);
//		compraInicial.setNomeComprador(dto.nomeComprador());
//		compraInicial.setStatus("PROCESSANDO");
//
//		compraRepository.save(compraInicial);
//
//		PedidoFilaDTO envelop = new PedidoFilaDTO(pedidoId, dto);
//		rabbitTemplate.convertAndSend(
//						RabbitMQConfig.EXCHANGE_INGRESSOS,
//						RabbitMQConfig.ROUTING_KEY_INGRESSOS,
//						envelop
//		);
//
//
//		return ResponseEntity.accepted().body(
//						new RespostaPedidoDTO(pedidoId, "Pedido recebido! Processando sua reserva..."));
//	}

	@GetMapping("/status/{id}")
	public ResponseEntity<?> consultarStatus(@PathVariable UUID id) {
		CompraEntity compra = compraRepository.findById(id)
						.orElseThrow(() -> new RuntimeException("Compra não encontrada"));

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

//	@PostMapping("/confirma")
//	public ResponseEntity<> confirmaPagamento(){
//
//	}
}
