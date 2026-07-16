package com.cinemamq.cinemamq.infrastructure.model.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class CompraIngressoDTO implements Serializable {

	@NotNull(message = "É obrigado colocar o nome do usuário!")
	@Size(message = "Nome do usuário deve ter 5 a 100 caracteres", min = 5, max = 50)
	private String nomeComprador;

	@NotNull
	private UUID filmeId;

	@NotNull
	private String horario;

	@NotNull
	private UUID sala;

	@NotNull
	private UUID id;

	private List<ItemConsumoDTO> itensConsumo = new ArrayList<>();


	public CompraIngressoDTO(String nomeComprador, UUID filmeId, String horario, UUID sala, UUID id, List<ItemConsumoDTO> itensConsumo ) {
		this.nomeComprador = nomeComprador;
		this.filmeId = filmeId;
		this.horario = horario;
		this.sala = sala;
		this.id = id;
		this.itensConsumo = itensConsumo;
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

	public List<ItemConsumoDTO> getItensConsumo() {
		return itensConsumo;
	}

	public void setItensConsumo(List<ItemConsumoDTO> itensConsumo) {
		this.itensConsumo = itensConsumo;
	}
}
