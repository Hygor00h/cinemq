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

		// 1. Converte o DTO base para a Entidade
		CompraEntity valor = compraMapper.toEntity(dto);

		// 2. Busca e valida se TODOS os assentos existem no banco
		List<AssentoEntity> assentosEntities = assentoRepository.findAllById(dto.getAssentosIds());

		if (assentosEntities.size() != dto.getAssentosIds().size()) {
			throw new RuntimeException("Um ou mais assentos informados não existem no banco!");
		}

		// 3. Valida se algum assento já está ocupado no banco ou tem compra aprovada
		boolean algumAssentoOcupado = assentosEntities.stream().anyMatch(AssentoEntity::getOcupado)
						|| dto.getAssentosIds().stream().anyMatch(id -> compraRepository.existsByAssento_IdAndStatus(id, "SUCESSO"));

		if (algumAssentoOcupado) {
			throw new EventFullException("Um ou mais assentos selecionados já estão ocupados!");
		}

		// 4. Busca Sala e Filme
		SalaEntity salaEntity = salaRepository.findById(dto.getSala())
						.orElseThrow(() -> new RuntimeException("Sala não encontrada"));
		FilmeEntity filmeEntity = filmeRepository.findById(dto.getFilmeId())
						.orElseThrow(() -> new RuntimeException("Filme não encontrado"));

		// 5. Preenche os dados do pedido
		valor.setId(pedidoId);
		valor.setStatus("PROCESSANDO");
		valor.setHorario(dto.getHorario());
		valor.setNomeComprador(dto.getNomeComprador());
		valor.setFilme(filmeEntity);
		valor.setSala(salaEntity);
		valor.setAssento(assentosEntities);

		// 6. Preenche os produtos de consumo (Bomboniere)
		if (dto.getItensConsumo() != null && !dto.getItensConsumo().isEmpty()) {
			List<CompraProdutoEntity> produtosDaCompra = dto.getItensConsumo().stream().map(itemDto -> {
				CompraProdutoEntity compraProduto = new CompraProdutoEntity();

				ProdutosEntitys produto = produtoRepository.findById(itemDto.getProdutoId())
								.orElseThrow(() -> new RuntimeException("Produto não encontrado"));

				compraProduto.setCompra(valor);
				compraProduto.setProduto(produto);
				compraProduto.setQuantidade(itemDto.getQuantidade());
				compraProduto.setPrecoUnitario(produto.getPreco());

				return compraProduto;
			}).toList();

			valor.setItens(produtosDaCompra);
		}

		// 7. Salva a compra inicial com status PROCESSANDO
		compraRepository.save(valor);

		// 8. Envia para a Fila do RabbitMQ
		PedidoFilaDTO pedidoFila = new PedidoFilaDTO(pedidoId, dto);
		rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE_INGRESSOS, RabbitMQConfig.ROUTING_KEY_INGRESSOS, pedidoFila);

		// 9. Retorna o DTO de Resposta
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
			case "AGUARDANDO_PAGAMENTO" -> ResponseEntity.ok().body(compra.getStatus());
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
