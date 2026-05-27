package com.cinemamq.cinemamq.infrastructure.model.dto;

public record FilmeDto(String nome,
											 String genero,
											 String duracao,
											 Integer faixaEtaria,
											 Double valorIngresso) {
}
