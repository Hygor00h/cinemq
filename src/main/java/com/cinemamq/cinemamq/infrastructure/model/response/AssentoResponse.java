package com.cinemamq.cinemamq.infrastructure.model.response;

import java.util.UUID;

public class AssentoResponse {

	private UUID id;
	private Integer numero;
	private Boolean ocupado;


	public AssentoResponse(UUID id, Integer numero, Boolean ocupado) {
		this.id = id;
		this.numero = numero;
		this.ocupado = ocupado;
	}

	public AssentoResponse() {
	}

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public Integer getNumero() {
		return numero;
	}

	public void setNumero(Integer numero) {
		this.numero = numero;
	}

	public Boolean getOcupado() {
		return ocupado;
	}

	public void setOcupado(Boolean ocupado) {
		this.ocupado = ocupado;
	}
}
