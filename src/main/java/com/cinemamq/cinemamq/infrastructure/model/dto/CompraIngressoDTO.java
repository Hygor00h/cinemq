package com.cinemamq.cinemamq.infrastructure.model.dto;

import java.io.Serializable;
import java.util.UUID;

public class CompraIngressoDTO implements Serializable {

	private String nomeComprador;
	private UUID filmeId;
	private String horario;
	private UUID sala;
	private UUID id;

	public CompraIngressoDTO(String nomeComprador, UUID filmeId, String horario, UUID sala, UUID id) {
		this.nomeComprador = nomeComprador;
		this.filmeId = filmeId;
		this.horario = horario;
		this.sala = sala;
		this.id = id;
	}

	public CompraIngressoDTO() {
	}

	public String getNomeComprador() {
		return nomeComprador;
	}

	public void setNomeComprador(String nomeComprador) {
		this.nomeComprador = nomeComprador;
	}

	public UUID getFilmeId() {
		return filmeId;
	}

	public void setFilmeId(UUID filmeId) {
		this.filmeId = filmeId;
	}

	public String getHorario() {
		return horario;
	}

	public void setHorario(String horario) {
		this.horario = horario;
	}

	public UUID getSala() {
		return sala;
	}

	public void setSala(UUID sala) {
		this.sala = sala;
	}

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}
}
