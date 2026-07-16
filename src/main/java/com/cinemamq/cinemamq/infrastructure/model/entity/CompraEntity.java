package com.cinemamq.cinemamq.infrastructure.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.domain.Persistable;

import java.beans.XMLEncoder;
import java.math.BigDecimal;
import java.util.*;

@Entity
@Table(name = "compras")
public class CompraEntity implements Persistable<UUID> {
//criar o ingresso
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

	@OneToMany(mappedBy = "compra", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<CompraProdutoEntity> itens = new ArrayList<>();

	public CompraEntity() {
	}

	public CompraEntity(UUID id, String nomeComprador, String horario, String status, String mensagemErro, AssentoEntity assento, SalaEntity sala, FilmeEntity filme, List<CompraProdutoEntity> itens, boolean isNew) {
		this.id = id;
		this.nomeComprador = nomeComprador;
		this.horario = horario;
		this.status = status;
		this.mensagemErro = mensagemErro;
		this.assento = assento;
		this.sala = sala;
		this.filme = filme;
		this.itens = itens;
		this.isNew = isNew;
	}

	public CompraEntity(String s, String horario, FilmeEntity filme, AssentoEntity assento, Double valorIngresso, List<CompraProdutoEntity> itens) {
	}


	// 💡 Método para calcular o valor total (Ingresso + Lanchonete)
	public BigDecimal getCalculaValorTotal() {
		BigDecimal total = BigDecimal.ZERO;

		// 1. Soma o valor do ingresso se o filme estiver associado
		if (this.filme != null && this.filme.getValorIngresso() != null) {
			total = total.add(this.filme.getValorIngresso());
		}

		// 2. Soma o valor de cada produto da lanchonete (Preço x Quantidade)
		if (this.itens != null && !this.itens.isEmpty()) {
			for (CompraProdutoEntity item : this.itens) {
				// Altere getPrecoUnitario() e getQuantidade() para os nomes reais da sua CompraProdutoEntity
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

	public List<CompraProdutoEntity> getItens() {
		return itens;
	}

	public void setItens(List<CompraProdutoEntity> itens) {
		this.itens = itens;
	}
}
