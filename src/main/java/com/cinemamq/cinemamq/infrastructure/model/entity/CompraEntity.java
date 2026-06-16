package com.cinemamq.cinemamq.infrastructure.model.entity;

import jakarta.persistence.*;
import org.springframework.data.domain.Persistable;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "compras")
public class CompraEntity implements Persistable<UUID> {

	@Id
	private UUID id;

	@Column(name = "nome_comprador")
	private String nomeComprador;

	private String horario;
	private String status;

	@Column(name = "mensagem_erro")
	private String mensagemErro;


	@ManyToOne
	@JoinColumn(name = "assento_id")
	private AssentoEntity assento;

	@ManyToOne
	@JoinColumn(name = "sala_id")
	private SalaEntity sala;

	@ManyToOne
	@JoinColumn(name = "filme_id")
	private FilmeEntity filme;

	public CompraEntity() {
	}

	public CompraEntity(UUID id, String nomeComprador, String horario, String status, String mensagemErro, FilmeEntity filme, AssentoEntity assento) {
		this.id = id;
		this.nomeComprador = nomeComprador;
		this.horario = horario;
		this.status = status;
		this.mensagemErro = mensagemErro;
		this.filme = filme;
		this.assento = assento;
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

	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}

	public String getMensagemErro() {
		return mensagemErro;
	}

	public void setMensagemErro(String mensagemErro) {
		this.mensagemErro = mensagemErro;
	}

	public SalaEntity getSala() {
		return sala;
	}

	public void setSala(SalaEntity sala) {
		this.sala = sala;
	}
}
