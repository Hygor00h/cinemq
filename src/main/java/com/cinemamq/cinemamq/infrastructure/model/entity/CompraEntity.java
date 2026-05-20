package com.cinemamq.cinemamq.infrastructure.model.entity;

import jakarta.persistence.*;
import org.springframework.data.domain.Persistable;

import java.util.UUID;

@Entity
@Table(name = "compras")
public class CompraEntity implements Persistable<UUID> {

	@Id
	private UUID id;

	private String nomeComprador;

	private String horario;

	private String status;

	private Integer numeroAssento;

	private String mensagemErro;

	@ManyToOne
	@JoinColumn
	private FilmeEntity filme;

	@ManyToOne
	@JoinColumn
	private AssentoEntity assento;

	private Double valorPago;

	public CompraEntity() {
	}

	public CompraEntity(UUID id, String nomeComprador, String horario, String status, Integer numeroAssento, String mensagemErro, FilmeEntity filme, AssentoEntity assento, Double valorPago) {
		this.id = id;
		this.nomeComprador = nomeComprador;
		this.horario = horario;
		this.status = status;
		this.numeroAssento = numeroAssento;
		this.mensagemErro = mensagemErro;
		this.filme = filme;
		this.assento = assento;
		this.valorPago = valorPago;
	}

	public CompraEntity(String s, String horario, FilmeEntity filme, AssentoEntity assento, Double valorIngresso) {
	}


	@Transient
	private boolean isNew = true;

	@PostLoad
	@PostPersist
	public void markNotNew() {
		this.isNew = false; // Se buscou do banco ou acabou de salvar, não é mais nova
	}

	@Override
	public boolean isNew() {
		return this.isNew; // O Spring Data vai ler isso aqui antes de decidir entre persist e merge
	}

	@Override
	public UUID getId() {
		return this.id;
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

	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}

	public Integer getNumeroAssento() {
		return numeroAssento;
	}

	public void setNumeroAssento(Integer numeroAssento) {
		this.numeroAssento = numeroAssento;
	}

	public String getMensagemErro() {
		return mensagemErro;
	}

	public void setMensagemErro(String mensagemErro) {
		this.mensagemErro = mensagemErro;
	}
}
