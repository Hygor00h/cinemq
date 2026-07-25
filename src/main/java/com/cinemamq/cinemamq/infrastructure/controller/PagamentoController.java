package com.cinemamq.cinemamq.infrastructure.controller;


import com.cinemamq.cinemamq.infrastructure.model.dto.PagamentoDTO;
import com.cinemamq.cinemamq.infrastructure.model.entity.CompraEntity;
import com.cinemamq.cinemamq.infrastructure.repository.CompraRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/pagamentos")
public class PagamentoController {



	private final CompraRepository compraRepository;

	public PagamentoController(CompraRepository compraRepository) {
		this.compraRepository = compraRepository;
	}

	@PostMapping("/simular")
	public ResponseEntity<String> simularPagamento(@RequestBody @Valid PagamentoDTO dto) {
		CompraEntity compra = compraRepository.findById(dto.getPedidoId())
						.orElseThrow(() -> new RuntimeException("Compra não encontrada"));
		if ("SUCESSO".equals(compra.getStatus())) {
			return ResponseEntity.badRequest().body("Este pedido já foi pago!");
		}
		if (!"AGUARDANDO_PAGAMENTO".equals(compra.getStatus())) {
			return ResponseEntity.badRequest().body("Este pedido não está disponível para pagamento. Status atual: " + compra.getStatus());
		}
		BigDecimal valorEsperado = compra.getCalculaValorTotal();
		if (dto.getValor() == null || dto.getValor().compareTo(valorEsperado) != 0) {
			compra.setStatus("PAGAMENTO_INCORRETO");
			compra.setMensagemErro("Valor pago (" + dto.getValor() + ") é diferente do valor cobrado (" + valorEsperado + ")");
			compraRepository.save(compra);
			return ResponseEntity.status(HttpStatus.PAYMENT_REQUIRED)
							.body("Valor incorreto! O valor necessário é R$ " + valorEsperado);
		}
		compra.setStatus("SUCESSO");
		compra.setMensagemErro(null);
		compraRepository.save(compra);
		return ResponseEntity.ok("Pagamento de R$ " + dto.getValor() + " processado com sucesso! Pedido finalizado.");
	}

}
