package com.cinemamq.cinemamq.infrastructure.model.response;

import java.math.BigDecimal;
import java.util.List;

public class CompraResponse {

	private String idCompra;
	private String nomeComprador;
	private String filme;
	private String horario;
	private String sala;
	private String cadeira;
	private BigDecimal valorIngresso;
	private BigDecimal valorTotal;
	private List<String> itensConsumo;

	public CompraResponse(String nomeComprador, String filme, String horario, String sala, String numero, BigDecimal valor,BigDecimal valorTotal,List<String> itensConsumo, String idCompra) {
		this.nomeComprador = nomeComprador;
		this.filme = filme;
		this.horario = horario;
		this.sala = sala;
		this.cadeira = numero;
		this.valorIngresso = valor;
		this.valorTotal = valorTotal;
		this.itensConsumo = itensConsumo;
		this.idCompra = idCompra;
	}

	public CompraResponse() {
	}

	public String getNomeComprador() {
		return nomeComprador;
	}

	public void setNomeComprador(String nomeComprador) {
		this.nomeComprador = nomeComprador;
	}

	public String getFilme() {
		return filme;
	}

	public void setFilme(String filme) {
		this.filme = filme;
	}

	public String getHorario() {
		return horario;
	}

	public void setHorario(String horario) {
		this.horario = horario;
	}

	public String getSala() {
		return sala;
	}

	public void setSala(String sala) {
		this.sala = sala;
	}

	public String getCadeira() {
		return cadeira;
	}

	public void setCadeira(String cadeira) {
		this.cadeira = cadeira;
	}

	public BigDecimal getValorIngresso() {
		return valorIngresso;
	}

	public void setValorIngresso(BigDecimal valorIngresso) {
		this.valorIngresso = valorIngresso;
	}

	public BigDecimal getValorTotal() {
		return valorTotal;
	}

	public void setValorTotal(BigDecimal valorTotal) {
		this.valorTotal = valorTotal;
	}

	public List<String> getItensConsumo() {
		return itensConsumo;
	}

	public void setItensConsumo(List<String> itensConsumo) {
		this.itensConsumo = itensConsumo;
	}

	public String getIdCompra() {
		return idCompra;
	}

	public void setIdCompra(String idCompra) {
		this.idCompra = idCompra;
	}
}
