package com.cinemamq.cinemamq.infrastructure.model.dto;

import com.cinemamq.cinemamq.infrastructure.model.entity.AssentoEntity;

import java.io.Serializable;
import java.util.UUID;

public record CompraIngressoDTO(String nomeComprador,
																UUID filmeId,
																String horario,
																Long id) implements Serializable {
}
