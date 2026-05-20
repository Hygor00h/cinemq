package com.cinemamq.cinemamq.infrastructure.model.dto;

import java.io.Serializable;
import java.util.UUID;

public record CompraIngressoDTO(String nomeComprador,
																UUID filmeId,
																String horario,
																Long assentoId) implements Serializable {
}
