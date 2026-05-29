package com.cinemamq.cinemamq.infrastructure.model.entity;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "salas")
public class SalaEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private UUID id;

	@Column(name = "nome_sala")
	private String nomeSala;


	@ManyToOne
	@JoinColumn(name = "filme_id")
	private FilmeEntity filme;

	public SalaEntity() {
	}

	public SalaEntity(UUID id, String nomeSala, FilmeEntity filme) {
		this.id = id;
		this.nomeSala = nomeSala;
		this.filme = filme;
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

	public FilmeEntity getFilme() {
		return filme;
	}

	public void setFilme(FilmeEntity filme) {
		this.filme = filme;
	}
}
