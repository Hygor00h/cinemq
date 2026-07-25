package com.cinemamq.cinemamq.infrastructure.model.response;

import com.cinemamq.cinemamq.infrastructure.model.entity.AssentoEntity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public class AssentoResponse {

	private UUID id;
	private Integer numero;
	private Boolean ocupado;

//	@OneToMany(mappedBy = "sala", fetch = FetchType.LAZY)
//	private Set<AssentoEntity> assentos = new LinkedHashSet<>();

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
