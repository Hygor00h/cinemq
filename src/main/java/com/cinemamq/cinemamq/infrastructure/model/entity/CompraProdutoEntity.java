package com.cinemamq.cinemamq.infrastructure.model.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "compra_produtos")
public class CompraProdutoEntity {

	@Id
	@GeneratedValue(generator = "UUID")
	private UUID id;

	@ManyToOne
	@JoinColumn(name = "compra_id", nullable = false)
	private CompraEntity compra;

	@ManyToOne
	@JoinColumn(name = "produto_id", nullable = false)

	private ProdutosEntitys produto; // 🌟 Aqui sim você aponta para o Produto

	private Integer quantidade;

	private BigDecimal precoUnitario;

	public CompraProdutoEntity(UUID id, CompraEntity compra, ProdutosEntitys produto, Integer quantidade, BigDecimal precoUnitario) {
		this.id = id;
		this.compra = compra;
		this.produto = produto;
		this.quantidade = quantidade;
		this.precoUnitario = precoUnitario;
	}

	public CompraProdutoEntity() {
	}

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public CompraEntity getCompra() {
		return compra;
	}

	public void setCompra(CompraEntity compra) {
		this.compra = compra;
	}

	public ProdutosEntitys getProduto() {
		return produto;
	}

	public void setProduto(ProdutosEntitys produto) {
		this.produto = produto;
	}

	public Integer getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(Integer quantidade) {
		this.quantidade = quantidade;
	}

	public BigDecimal getPrecoUnitario() {
		return precoUnitario;
	}

	public void setPrecoUnitario(BigDecimal precoUnitario) {
		this.precoUnitario = precoUnitario;
	}
}
