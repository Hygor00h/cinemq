package com.cinemamq.cinemamq.infrastructure.model.dto;

import java.io.Serializable;

public class FilmeDto implements Serializable {

	private String nome;
	private String genero;
	private String duracao;
	private Integer faixaEtaria;
	private Double valorIngresso;

	public FilmeDto(String nome, String genero, String duracao, Integer faixaEtaria, Double valorIngresso) {
		this.nome = nome;
		this.genero = genero;
		this.duracao = duracao;
		this.faixaEtaria = faixaEtaria;
		this.valorIngresso = valorIngresso;
	}

	public FilmeDto() {
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
