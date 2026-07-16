package com.cinemamq.cinemamq.infrastructure.mapper;

import com.cinemamq.cinemamq.infrastructure.model.dto.CompraIngressoDTO;
import com.cinemamq.cinemamq.infrastructure.model.entity.CompraEntity;
import com.cinemamq.cinemamq.infrastructure.model.entity.CompraProdutoEntity;
import com.cinemamq.cinemamq.infrastructure.model.response.CompraResponse;
import org.mapstruct.*;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CompraMapper {

	// 1. De Entity para DTO (Pegamos o ID da sala e jogamos no UUID do DTO)
	// 💡 Nota: Ajuste o source "sala.id" se o campo na CompraEntity tiver outro nome
	@Mapping(target = "sala", source = "sala.id")
	@Mapping(target = "filmeId", source = "filme.id")
	CompraIngressoDTO toDto(CompraEntity compraEntity);

	// 2. Mapeamento de LISTAS correto (Mudamos os tipos para List)
	List<CompraIngressoDTO> toDtoList(List<CompraEntity> compras);
	List<CompraEntity> toEntityList(List<CompraIngressoDTO> dtos);


	// 3. De DTO para Entity (Para salvar no Banco)
	// 💡 IGNORE: Avisamos ao MapStruct para não tentar mapear o UUID para a SalaEntity complexa
	@Mapping(target = "sala", ignore = true)
	@Mapping(target = "filme", ignore = true)
	@Mapping(target = "assento", ignore = true)
	@Mapping(target = "itens", ignore = true)
	CompraEntity toEntity(CompraIngressoDTO dto);

	@Mapping(target = "nomeComprador", source = "nomeComprador")
	@Mapping(target = "filme", source = "filme.nome")
	@Mapping(target = "horario", source = "horario")
	@Mapping(target = "sala", source = "sala.nomeSala")
	@Mapping(target = "cadeira", source = "assento.numero") // 🌟 Ajustado para "numeroVisivel" conforme seu SQL
	@Mapping(target = "valorIngresso", source = "filme.valorIngresso")
	@Mapping(target = "itensConsumo", source = "itens")
	CompraResponse toResponse(CompraEntity compraEntity);
	//@Mapping(target = "valorTotal", expression = "java(compraEntity.getCalculaValorTotal())")
	default String mapCompraProdutoToString(CompraProdutoEntity item) {
		if (item == null || item.getProduto() == null) {
			return "";
		}
		// Retorna no formato: "2x Pipoca"
		return item.getQuantidade() + "x " + item.getProduto().getNome();
	}

	@AfterMapping
	default void calcularValorTotal(CompraEntity entity, @MappingTarget CompraResponse response) {
		if (entity != null && response != null) {
			// Seta o valor total calculado diretamente no DTO de resposta de forma 100% Java
			response.setValorTotal(entity.getCalculaValorTotal());
		}
	}

}
