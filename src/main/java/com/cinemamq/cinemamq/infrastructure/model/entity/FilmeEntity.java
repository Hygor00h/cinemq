package com.cinemamq.cinemamq.infrastructure.model.entity;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "filmes")
public class FilmeEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID id;

	private String nome;
	private String genero;
	private String duracao;

	@Column(name = "faixa_etaria")
	private Integer faixaEtaria;

	@Column(name = "valor_ingresso")
	private Double valorIngresso;

	@OneToMany(mappedBy = "filme")
	private List<SalaEntity> salas = new ArrayList<>();

	public FilmeEntity() {
	}

	public FilmeEntity(UUID id, String nome, String genero, String duracao, Integer faixaEtaria, Double valorIngresso) {
		this.id = id;
		this.nome = nome;
		this.genero = genero;
		this.duracao = duracao;
		this.faixaEtaria = faixaEtaria;
		this.valorIngresso = valorIngresso;
	}

	public FilmeEntity(String batman, String s, String s1, int i, double v) {
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

	public String getGenero() {
		return genero;
	}

	public void setGenero(String genero) {
		this.genero = genero;
	}

	public String getDuracao() {
		return duracao;
	}

	public void setDuracao(String duracao) {
		this.duracao = duracao;
	}

	public Integer getFaixaEtaria() {
		return faixaEtaria;
	}

	public void setFaixaEtaria(Integer faixaEtaria) {
		this.faixaEtaria = faixaEtaria;
	}

	public Double getValorIngresso() {
		return valorIngresso;
	}

	public void setValorIngresso(Double valorIngresso) {
		this.valorIngresso = valorIngresso;
	}
}
