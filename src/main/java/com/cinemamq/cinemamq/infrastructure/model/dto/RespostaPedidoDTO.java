package com.cinemamq.cinemamq.infrastructure.model.dto;

import java.io.Serializable;
import java.util.UUID;

public class RespostaPedidoDTO implements Serializable {

	private UUID pedidoId;
	private String mensagem;

	public RespostaPedidoDTO(UUID pedidoId, String mensagem) {
		this.pedidoId = pedidoId;
		this.mensagem = mensagem;
	}

	public RespostaPedidoDTO() {
	}

	public UUID getPedidoId() {
		return pedidoId;
	}

	public void setPedidoId(UUID pedidoId) {
		this.pedidoId = pedidoId;
	}

	public String getMensagem() {
		return mensagem;
	}

	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}
}
