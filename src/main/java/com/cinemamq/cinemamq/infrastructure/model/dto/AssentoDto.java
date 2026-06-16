package com.cinemamq.cinemamq.infrastructure.model.dto;

import java.io.Serializable;
import java.util.UUID;

public class AssentoDto implements Serializable {

	private UUID id;
	private Integer numero;
	private Boolean ocupado;

	public AssentoDto(UUID id, Integer numero, Boolean ocupado) {
		this.id = id;
		this.numero = numero;
		this.ocupado = ocupado;
	}

	public AssentoDto() {
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
