package com.cinemamq.cinemamq.infrastructure.mapper;


import com.cinemamq.cinemamq.infrastructure.model.dto.AssentoDto;
import com.cinemamq.cinemamq.infrastructure.model.entity.AssentoEntity;
import com.cinemamq.cinemamq.infrastructure.model.response.AssentoResponse;
import org.mapstruct.Mapper;


import java.util.List;
import java.util.Set;

@Mapper
public interface AssentoMapper {

	// 1. Mapeamento individual (elemento a elemento)
	AssentoResponse toResponse(AssentoEntity assento);

	// 2. Mapeamento de Coleção (MapStruct usa o método individual acima automaticamente!)
	List<AssentoResponse> toResponseList(Set<AssentoEntity> assentos);


	AssentoDto toDto(AssentoEntity entity);
	AssentoEntity toEntity(AssentoDto dto);


	Set<AssentoDto> toDtoSet(Set<AssentoEntity> entities);
	Set<AssentoEntity> toEntitySet(Set<AssentoDto> dtos);
}
