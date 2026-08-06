package com.cinemamq.cinemamq.infrastructure.model.entity;

import com.cinemamq.cinemamq.infrastructure.model.enums.StatusProcesso;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.domain.Persistable;

import java.beans.XMLEncoder;
import java.math.BigDecimal;
import java.util.*;

@Entity
@Table(name = "compras")
public class CompraEntity implements Persistable<UUID> {

	@Id
	private UUID id;

	@Column(name = "nome_comprador")
	private String nomeComprador;

	private String horario;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false, length = 20)
	private StatusProcesso status;

	@Column(name = "mensagem_erro")
	private String mensagemErro;



	@ManyToMany
	@JoinTable(
					name = "compra_assento", // Nome da tabela intermediária no banco
					joinColumns = @JoinColumn(name = "compra_id"),
					inverseJoinColumns = @JoinColumn(name = "assento_id"))
	private List<AssentoEntity> assento;

	@ManyToOne
	@JoinColumn(name = "sala_id")
	private SalaEntity sala;

	@ManyToOne
	@JoinColumn(name = "filme_id")
	private FilmeEntity filme;

	@OneToMany(mappedBy = "compra", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<CompraProdutoEntity> itens = new ArrayList<>();

	public CompraEntity() {
	}

	public CompraEntity(UUID id, String nomeComprador, String horario, StatusProcesso status, String mensagemErro, List<AssentoEntity> assento, SalaEntity sala, FilmeEntity filme, List<CompraProdutoEntity> itens) {
		this.id = id;
		this.nomeComprador = nomeComprador;
		this.horario = horario;
		this.status = status;
		this.mensagemErro = mensagemErro;
		this.assento = assento;
		this.sala = sala;
		this.filme = filme;
		this.itens = itens;
	}

	public CompraEntity(String s, String horario, FilmeEntity filme, AssentoEntity assento, Double valorIngresso, List<CompraProdutoEntity> itens) {
	}


	public BigDecimal getCalculaValorTotal() {
		BigDecimal total = BigDecimal.ZERO;

		if (this.filme != null && this.filme.getValorIngresso() != null && this.assento != null && !this.assento.isEmpty()) {
			BigDecimal qtdIngressos = BigDecimal.valueOf(this.assento.size());
			BigDecimal totalIngressos = this.filme.getValorIngresso().multiply(qtdIngressos);
			total = total.add(totalIngressos);
		}

		if (this.itens != null && !this.itens.isEmpty()) {
			for (CompraProdutoEntity item : this.itens) {
				if (item.getPrecoUnitario() != null && item.getQuantidade() != null) {
					BigDecimal qtd = BigDecimal.valueOf(item.getQuantidade());
					BigDecimal valorTotalItem = item.getPrecoUnitario().multiply(qtd);
					total = total.add(valorTotalItem);
				}
			}
		}

		return total;
	}


	@Transient
	private boolean isNew = true;

	@PostLoad
	@PostPersist
	public void markNotNew() {
		this.isNew = false;
	}

	@Override
	public boolean isNew() {
		return this.isNew;
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

	public List<AssentoEntity> getAssento() {
		return assento;
	}

	public void setAssento(List<AssentoEntity> assento) {
		this.assento = assento;
	}

	public StatusProcesso getStatus() {
		return status;
	}
	public void setStatus(StatusProcesso status) {
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

	public List<CompraProdutoEntity> getItens() {
		return itens;
	}

	public void setItens(List<CompraProdutoEntity> itens) {
		this.itens = itens;
	}
}
