package com.cinemamq.cinemamq.infrastructure.model.response;

import java.util.UUID;

public class SalaResponse {

	private UUID id;
	private String nomeSala;

	public SalaResponse(UUID id, String nomeSala) {
		this.id = id;
		this.nomeSala = nomeSala;
	}

	public SalaResponse() {
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
}
