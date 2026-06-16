package com.cinemamq.cinemamq.infrastructure.controller;

import com.cinemamq.cinemamq.infrastructure.config.RabbitMQConfig;
import com.cinemamq.cinemamq.infrastructure.mapper.CompraMapper;
import com.cinemamq.cinemamq.infrastructure.model.dto.CompraIngressoDTO;
import com.cinemamq.cinemamq.infrastructure.model.dto.PedidoFilaDTO;
import com.cinemamq.cinemamq.infrastructure.model.entity.CompraEntity;
import com.cinemamq.cinemamq.infrastructure.model.response.CompraResponse;
import com.cinemamq.cinemamq.infrastructure.repository.AssentoRepository;
import com.cinemamq.cinemamq.infrastructure.repository.CompraRepository;
import com.cinemamq.cinemamq.infrastructure.repository.FilmeRepository;
import com.cinemamq.cinemamq.infrastructure.repository.SalaRepository;
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

	@Autowired
	private AssentoRepository assentoRepository;

	@Autowired
	private FilmeRepository filmeRepository;

	@Autowired
	private SalaRepository salaRepository;

	@Autowired
	private CompraMapper compraMapper;

	@PostMapping("/comprar")
	public ResponseEntity<CompraResponse> comprar(@RequestBody CompraIngressoDTO dto) {
		UUID pedidoId = UUID.randomUUID();

		CompraEntity compra = new CompraEntity();

		CompraEntity valor = compraMapper.toEntity(dto);

		valor.setId(pedidoId);
		valor.setStatus("PROCESSANDO");
		valor.setHorario(dto.getHorario());
		valor.setNomeComprador(dto.getNomeComprador());

		valor.setSala(salaRepository.findById(dto.getSala()).orElseThrow(() -> new RuntimeException("Sala não encontrada")));
		valor.setFilme(filmeRepository.findById(dto.getFilmeId()).orElseThrow(() -> new RuntimeException("Filme não encontrado")));
		valor.setAssento(assentoRepository.findById(dto.getId()).orElseThrow(() -> new RuntimeException("Assento não encontrado")));

		compraRepository.save(valor);

		PedidoFilaDTO pedidoFila = new PedidoFilaDTO(pedidoId, dto);

		rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE_INGRESSOS, RabbitMQConfig.ROUTING_KEY_INGRESSOS, pedidoFila);

		return ResponseEntity.accepted().body(compraMapper.toResponse(valor));
	}


	@GetMapping("/status/{id}")
	public ResponseEntity<?> consultarStatus(@PathVariable UUID id) {
		CompraEntity compra = compraRepository.findById(id)
						.orElseThrow(() -> new RuntimeException("Compra não encontrada"));

		if (compra == null) {
			return ResponseEntity.notFound().build();
		}

		return switch (compra.getStatus()) {
			case "PROCESSANDO" -> ResponseEntity.ok().body(compra.getStatus());
			case "SUCESSO" -> ResponseEntity.ok(compra);
			case "ESGOTADO" -> ResponseEntity.badRequest().body(compra.getStatus() + "\"O assento selecionado já foi ocupado por outro cliente.\"");
			default -> ResponseEntity.internalServerError().build();
		};
	}

//	@PostMapping("/confirma")
//	public ResponseEntity<> confirmaPagamento(){
//
//	}
}
