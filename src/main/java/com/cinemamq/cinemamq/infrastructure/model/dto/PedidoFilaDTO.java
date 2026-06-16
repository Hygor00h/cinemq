package com.cinemamq.cinemamq.infrastructure.model.dto;

import java.io.Serializable;
import java.util.UUID;

public class PedidoFilaDTO implements Serializable {

	private UUID pedidoId;
	private CompraIngressoDTO dados;

	public PedidoFilaDTO(UUID pedidoId, CompraIngressoDTO dados) {
		this.pedidoId = pedidoId;
		this.dados = dados;
	}

	public PedidoFilaDTO() {
	}

	public UUID getPedidoId() {
		return pedidoId;
	}

	public void setPedidoId(UUID pedidoId) {
		this.pedidoId = pedidoId;
	}

	public CompraIngressoDTO getDados() {
		return dados;
	}

	public void setDados(CompraIngressoDTO dados) {
		this.dados = dados;
	}
}
