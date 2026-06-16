package com.cinemamq.cinemamq.infrastructure.model.dto;

import java.io.Serializable;
import java.util.Set;
import java.util.UUID;

public class SalaDto implements Serializable {

	private UUID id;
	private String nomeSala;
	private UUID filme;
	private Set<AssentoDto> assentos;

	public SalaDto(UUID id, String nomeSala, UUID filme, Set<AssentoDto> assentos) {
		this.id = id;
		this.nomeSala = nomeSala;
		this.filme = filme;
		this.assentos = assentos;
	}

	public SalaDto() {
	}

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public String getNomeSala() {
		return nomeSala;
	}

	public void setNomeSala(String nomeSala) {
		this.nomeSala = nomeSala;
	}

	public UUID getFilme() {
		return filme;
	}

	public void setFilme(UUID filme) {
		this.filme = filme;
	}

	public Set<AssentoDto> getAssentos() {
		return assentos;
	}

	public void setAssentos(Set<AssentoDto> assentos) {
		this.assentos = assentos;
	}
}
