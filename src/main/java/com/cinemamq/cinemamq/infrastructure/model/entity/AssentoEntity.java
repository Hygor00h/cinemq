package com.cinemamq.cinemamq.infrastructure.model.entity;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "assentos")
public class AssentoEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID id;

	private Integer numero;
	private Boolean ocupado = false;

	@ManyToOne
	@JoinColumn(name = "filme_id")
	private FilmeEntity filme;

	public AssentoEntity() {
	}

	public AssentoEntity(UUID id, Integer numero, Boolean ocupado, FilmeEntity filme) {
		this.id = id;
		this.numero = numero;
		this.ocupado = ocupado;
		this.filme = filme;
	}

	public AssentoEntity(int i, FilmeEntity batman) {
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

	public FilmeEntity getFilme() {
		return filme;
	}

	public void setFilme(FilmeEntity filme) {
		this.filme = filme;
	}

	public boolean isOcupado() {
		return ocupado;
	}
}
