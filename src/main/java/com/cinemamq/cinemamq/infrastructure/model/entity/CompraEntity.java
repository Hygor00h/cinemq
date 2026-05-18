package com.cinemamq.cinemamq.infrastructure.model.entity;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "compras")
public class CompraEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID id;

	private String nomeComprador;

	private String horario;

	@ManyToOne
	@JoinColumn
	private FilmeEntity filme;

	@ManyToOne
	@JoinColumn
	private AssentoEntity assento;

	private Double valorPago;

	public CompraEntity() {
	}

	public CompraEntity(UUID id, String nomeComprador, String horario, FilmeEntity filme, AssentoEntity assento, Double valorPago) {
		this.id = id;
		this.nomeComprador = nomeComprador;
		this.horario = horario;
		this.filme = filme;
		this.assento = assento;
		this.valorPago = valorPago;
	}

	public CompraEntity(String s, String horario, FilmeEntity filme, AssentoEntity assento, Double valorIngresso) {
	}


	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public String getNomeComprador() {
		return nomeComprador;
	}

	public void setNomeComprador(String nomeComprador) {
		this.nomeComprador = nomeComprador;
	}

	public String getHorario() {
		return horario;
	}

	public void setHorario(String horario) {
		this.horario = horario;
	}

	public FilmeEntity getFilme() {
		return filme;
	}

	public void setFilme(FilmeEntity filme) {
		this.filme = filme;
	}

	public AssentoEntity getAssento() {
		return assento;
	}

	public void setAssento(AssentoEntity assento) {
		this.assento = assento;
	}

	public Double getValorPago() {
		return valorPago;
	}

	public void setValorPago(Double valorPago) {
		this.valorPago = valorPago;
	}
}
