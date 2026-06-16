package com.cinemamq.cinemamq.infrastructure.mapper;

import com.cinemamq.cinemamq.infrastructure.model.dto.CompraIngressoDTO;
import com.cinemamq.cinemamq.infrastructure.model.entity.CompraEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CompraMapper {

	// 1. De Entity para DTO (Pegamos o ID da sala e jogamos no UUID do DTO)
	// 💡 Nota: Ajuste o source "sala.id" se o campo na CompraEntity tiver outro nome
	@Mapping(target = "sala", source = "compraEntity.sala.id")
	CompraIngressoDTO toDto(CompraEntity compraEntity);

	// 2. Mapeamento de LISTAS correto (Mudamos os tipos para List)
	List<CompraIngressoDTO> toDtoList(List<CompraEntity> compras);
	List<CompraEntity> toEntityList(List<CompraIngressoDTO> dtos);

	// 3. De DTO para Entity (Para salvar no Banco)
	// 💡 IGNORE: Avisamos ao MapStruct para não tentar mapear o UUID para a SalaEntity complexa
	@Mapping(target = "sala", ignore = true)
	CompraEntity toEntity(CompraIngressoDTO dto);
}
