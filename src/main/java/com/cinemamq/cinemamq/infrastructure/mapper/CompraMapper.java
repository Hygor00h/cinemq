package com.cinemamq.cinemamq.infrastructure.mapper;

import com.cinemamq.cinemamq.infrastructure.model.dto.CompraIngressoDTO;
import com.cinemamq.cinemamq.infrastructure.model.entity.AssentoEntity;
import com.cinemamq.cinemamq.infrastructure.model.entity.CompraEntity;
import com.cinemamq.cinemamq.infrastructure.model.entity.CompraProdutoEntity;
import com.cinemamq.cinemamq.infrastructure.model.response.CompraResponse;
import org.mapstruct.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CompraMapper {

	@Mapping(target = "sala", source = "sala.id")
	@Mapping(target = "filmeId", source = "filme.id")
	@Mapping(target = "assentosIds", source = "assento")
	CompraIngressoDTO toDto(CompraEntity compraEntity);

	default UUID mapAssentoToUuid(AssentoEntity assento) {
		return (assento != null) ? assento.getId() : null;
	}

	List<CompraIngressoDTO> toDtoList(List<CompraEntity> compras);
	List<CompraEntity> toEntityList(List<CompraIngressoDTO> dtos);

	@Mapping(target = "id", ignore = true)
	@Mapping(target = "sala", ignore = true)
	@Mapping(target = "filme", ignore = true)
	@Mapping(target = "assento", ignore = true)
	@Mapping(target = "itens", ignore = true)
	CompraEntity toEntity(CompraIngressoDTO dto);

	@Mapping(target = "idCompra", source = "id")
	@Mapping(target = "nomeComprador", source = "nomeComprador")
	@Mapping(target = "filme", source = "filme.nome")
	@Mapping(target = "horario", source = "horario")
	@Mapping(target = "sala", source = "sala.nomeSala")
	@Mapping(target = "cadeira", source = "assento")
	@Mapping(target = "valorIngresso", source = "filme.valorIngresso")
	@Mapping(target = "itensConsumo", source = "itens")
	CompraResponse toResponse(CompraEntity compraEntity);

	default String mapAssentosToString(List<AssentoEntity> assentos) {
		if (assentos == null || assentos.isEmpty()) {
			return "";
		}
		return assentos.stream()
						.map(assento -> String.valueOf(assento.getNumero())) // Ou getNumeroVisivel() conforme sua entidade
						.collect(Collectors.joining(", "));
	}

	default String mapCompraProdutoToString(CompraProdutoEntity item) {
		if (item == null || item.getProduto() == null) {
			return "";
		}
		return item.getQuantidade() + "x " + item.getProduto().getNome();
	}

	@AfterMapping
	default void calcularValorTotal(CompraEntity entity, @MappingTarget CompraResponse response) {
		if (entity != null && response != null) {
			response.setValorTotal(entity.getCalculaValorTotal());
		}
	}

}