package com.cinemamq.cinemamq.infrastructure.model.entity;

import jakarta.persistence.*;

import java.util.*;

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

	@OneToMany(mappedBy = "sala", fetch = FetchType.LAZY)
	private Set<AssentoEntity> assentos = new LinkedHashSet<>();

	public SalaEntity() {
	}

	public SalaEntity(UUID id, String nomeSala, FilmeEntity filme) {
		this.id = id;
		this.nomeSala = nomeSala;
		this.filme = filme;
	}


	public Set<AssentoEntity> getAssentos() {
		return assentos;
	}

	public void setAssentos(Set<AssentoEntity> assentos) {
		this.assentos = assentos;
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
