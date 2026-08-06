package com.cinemamq.cinemamq.infrastructure.model.enums;

public enum StatusProcesso {

	PROCESSANDO("PROCESSANDO"),
	AGUARDANDO_PAGAMENTO("AGUARDANDO_PAGAMENTO"),
	SUCESSO("SUCESSO"),
	ESGOTADO("ESGOTADO"),
	PAGAMENTO_INCORRETO("PAGAMENTO_INCORRETO"),
	ERRO_VALIDACAO("ERRO_VALIDACAO");

	private String descricao;

	public String getDescricao() {
		return descricao;
	}
	
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	StatusProcesso(String descricao) {
		this.descricao = descricao;
	}
}
