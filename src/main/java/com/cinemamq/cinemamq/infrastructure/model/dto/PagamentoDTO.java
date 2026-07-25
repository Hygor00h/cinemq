package com.cinemamq.cinemamq.infrastructure.model.dto;

import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.util.UUID;

public class PagamentoDTO {

	@NotNull(message = "O ID do pedido é obrigatório")
	private UUID pedidoId;

	@NotNull(message = "O valor é obrigatório")
	private BigDecimal valor;

	public PagamentoDTO() {}

	public PagamentoDTO(UUID pedidoId, BigDecimal valor) {
		this.pedidoId = pedidoId;
		this.valor = valor;
	}

	public UUID getPedidoId() {
		return pedidoId;
	}

	public void setPedidoId(UUID pedidoId) {
		this.pedidoId = pedidoId;
	}

	public BigDecimal getValor() {
		return valor;
	}

	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}

}
