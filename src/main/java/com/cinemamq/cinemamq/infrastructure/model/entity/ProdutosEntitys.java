package com.cinemamq.cinemamq.infrastructure.model.entity;

import com.cinemamq.cinemamq.infrastructure.model.enums.ProdutosEnum;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "produtos")
public class ProdutosEntitys {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID id;

	private String nome;

	private String descricao;

	private BigDecimal preco;

	private String imagemUrl;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false, length = 20)
	private ProdutosEnum categoria;

	private Integer estoque;

	public ProdutosEntitys(UUID id, String nome, String descricao, BigDecimal preco, String imagemUrl, ProdutosEnum categoria, Integer estoque) {
		this.id = id;
		this.nome = nome;
		this.descricao = descricao;
		this.preco = preco;
		this.imagemUrl = imagemUrl;
		this.categoria = categoria;
		this.estoque = estoque;
	}

	public ProdutosEntitys() {
	}

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public BigDecimal getPreco() {
		return preco;
	}

	public void setPreco(BigDecimal preco) {
		this.preco = preco;
	}

	public String getImagemUrl() {
		return imagemUrl;
	}

	public void setImagemUrl(String imagemUrl) {
		this.imagemUrl = imagemUrl;
	}

	public ProdutosEnum getCategoria() {
		return categoria;
	}

	public void setCategoria(ProdutosEnum categoria) {
		this.categoria = categoria;
	}

	public Integer getEstoque() {
		return estoque;
	}

	public void setEstoque(Integer estoque) {
		this.estoque = estoque;
	}
}
