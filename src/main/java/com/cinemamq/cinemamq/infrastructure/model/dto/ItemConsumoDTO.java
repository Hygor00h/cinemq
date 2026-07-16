package com.cinemamq.cinemamq.infrastructure.model.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.io.Serializable;
import java.util.UUID;

public class ItemConsumoDTO implements Serializable {

	@NotNull
	private UUID produtoId;

	@NotNull
	@Min(1)
	private Integer quantidade;

	public ItemConsumoDTO() {}

	public ItemConsumoDTO(UUID produtoId, Integer quantidade) {
		this.produtoId = produtoId;
		this.quantidade = quantidade;
	}

	public UUID getProdutoId() { return produtoId; }
	public void setProdutoId(UUID produtoId) { this.produtoId = produtoId; }

	public Integer getQuantidade() { return quantidade; }
	public void setQuantidade(Integer quantity) { this.quantidade = quantity; }

}
