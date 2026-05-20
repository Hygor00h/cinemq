package com.cinemamq.cinemamq.infrastructure.model.dto;

import java.io.Serializable;
import java.util.UUID;

public record PedidoFilaDTO(UUID pedidoId,
														CompraIngressoDTO dados) implements Serializable {
}
