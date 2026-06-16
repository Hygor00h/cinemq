package com.cinemamq.cinemamq.infrastructure.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.UUID;


@Entity
@Table(name = "assentos")
public class AssentoEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private UUID id;

	@Column(name = "numero_visivel")
	private Integer numero;

	private Boolean ocupado = false;

	@ManyToOne
	@JoinColumn(name = "sala_id")
	@JsonIgnore
	private SalaEntity sala;

	public AssentoEntity() {
	}

	public AssentoEntity(UUID id, Integer numero, Boolean ocupado, SalaEntity sala) {
		this.id = id;
		this.numero = numero;
		this.ocupado = ocupado;
		this.sala = sala;
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

	public SalaEntity getSala() {
		return sala;
	}

	public void setSala(SalaEntity sala) {
		this.sala = sala;
	}

	public boolean isOcupado() {
		return ocupado;
	}
}
