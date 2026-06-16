package com.cinemamq.cinemamq.infrastructure.model.response;

import java.util.UUID;

public class CompraResponse {

	private String nomeComprador;
	private String filme;
	private String horario;
	private String sala;
	private Integer numero;

	public CompraResponse(String nomeComprador, String filme, String horario, String sala, Integer numero) {
		this.nomeComprador = nomeComprador;
		this.filme = filme;
		this.horario = horario;
		this.sala = sala;
		this.numero = numero;
	}

	public CompraResponse() {
	}

	public String getNomeComprador() {
		return nomeComprador;
	}

	public void setNomeComprador(String nomeComprador) {
		this.nomeComprador = nomeComprador;
	}

	public String getFilme() {
		return filme;
	}

	public void setFilme(String filme) {
		this.filme = filme;
	}

	public String getHorario() {
		return horario;
	}

	public void setHorario(String horario) {
		this.horario = horario;
	}

	public String getSala() {
		return sala;
	}

	public void setSala(String sala) {
		this.sala = sala;
	}

	public Integer getNumero() {
		return numero;
	}

	public void setNumero(Integer numero) {
		this.numero = numero;
	}
}
