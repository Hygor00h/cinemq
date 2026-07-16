package com.cinemamq.cinemamq.infrastructure.model.enums;

public enum ProdutosEnum {

	PIPOCA("PIPOCA"),
	BEBIDA("BEBIDA"),
	SALGADO("SALGADO"),
	DOCE("DOCE"),
	COLECIONAVEL("COLECIONAVEL");

	private String descricao;

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	private ProdutosEnum(String descricao) {
		this.descricao = descricao;
	}
}
