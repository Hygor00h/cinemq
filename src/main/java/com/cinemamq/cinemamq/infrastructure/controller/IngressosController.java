package com.cinemamq.cinemamq.infrastructure.controller;

import com.cinemamq.cinemamq.infrastructure.config.RabbitMQConfig;
import com.cinemamq.cinemamq.infrastructure.exception.EventFullException;
import com.cinemamq.cinemamq.infrastructure.mapper.CompraMapper;
import com.cinemamq.cinemamq.infrastructure.model.dto.CompraIngressoDTO;
import com.cinemamq.cinemamq.infrastructure.model.dto.PedidoFilaDTO;
import com.cinemamq.cinemamq.infrastructure.model.entity.*;
import com.cinemamq.cinemamq.infrastructure.model.response.CompraResponse;
import com.cinemamq.cinemamq.infrastructure.repository.*;
import jakarta.validation.Valid;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@CrossOrigin(origins = "*")
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

	@Autowired
	private ProdutoRepository	produtoRepository;

	@PostMapping("/comprar")
	public ResponseEntity<CompraResponse> comprar(@RequestBody @Valid CompraIngressoDTO dto) {
		UUID pedidoId = UUID.randomUUID();

		CompraEntity valor = compraMapper.toEntity(dto);

		boolean jaExiste = compraRepository.existsByPedidoId(dto.getId(), "SUCESSO");

		if (jaExiste) {
			System.out.println("Compra já ocupada!");
			throw new EventFullException("Esta cadeira já está ocupada!");
		}

//		valor.setSala(salaRepository.findById(dto.getSala()).orElseThrow(() -> new RuntimeException("Sala não encontrada")));
//		valor.setFilme(filmeRepository.findById(dto.getFilmeId()).orElseThrow(() -> new RuntimeException("Filme não encontrado")));
//		valor.setAssento(assentoRepository.findById(dto.getId()).orElseThrow(() -> new RuntimeException("Assento não encontrado")));

		SalaEntity salaEntity = salaRepository.findById(dto.getSala()).orElseThrow(() -> new RuntimeException("Sala não encontrada"));
		FilmeEntity filmeEntity = filmeRepository.findById(dto.getFilmeId()).orElseThrow(() -> new RuntimeException("Filme não encontrado"));
		AssentoEntity assentoEntity = assentoRepository.findById(dto.getId()).orElseThrow(() -> new RuntimeException("Assento não encontrado"));


		valor.setId(pedidoId);
		valor.setStatus("PROCESSANDO");
		valor.setHorario(dto.getHorario());
		valor.setNomeComprador(dto.getNomeComprador());
		valor.setFilme(filmeEntity);
		valor.setSala(salaEntity);
		valor.setAssento(assentoEntity);


		if (dto.getItensConsumo() != null && !dto.getItensConsumo().isEmpty()) {
			List<CompraProdutoEntity> produtosDaCompra = dto.getItensConsumo().stream().map(itemDto -> {
				CompraProdutoEntity compraProduto = new CompraProdutoEntity();

				// Busca o produto real no banco para pegar o nome e preço oficial da lanchonete
				ProdutosEntitys produto = produtoRepository.findById(itemDto.getProdutoId())
								.orElseThrow(() -> new RuntimeException("Produto não encontrado"));

				compraProduto.setCompra(valor); // Vincula a este pedido de compra
				compraProduto.setProduto(produto);
				compraProduto.setQuantidade(itemDto.getQuantidade());
				compraProduto.setPrecoUnitario(produto.getPreco()); // Garante o preço oficial do banco

				return compraProduto;
			}).toList();

			valor.setItens(produtosDaCompra); // Seta os itens populados na entidade!
		}

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
			case "ESGOTADO" ->
							ResponseEntity.badRequest().body(compra.getStatus() + "\"O assento selecionado já foi ocupado por outro cliente.\"");
			default -> ResponseEntity.internalServerError().build();
		};
	}

//	@PostMapping("/confirma")
//	public ResponseEntity<> confirmaPagamento(){
//
//	}
}
