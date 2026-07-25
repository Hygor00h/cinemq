package com.cinemamq.cinemamq.infrastructure.mapper;

import com.cinemamq.cinemamq.infrastructure.model.dto.SalaDto;
import com.cinemamq.cinemamq.infrastructure.model.entity.SalaEntity;
import com.cinemamq.cinemamq.infrastructure.model.response.SalaResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper
public interface SalaMapper {


	@Mapping(target = "filme", source = "salaEntity.filme.id")
	SalaDto toDto(SalaEntity salaEntity);

	List<SalaDto> toDtoList(List<SalaEntity> salas);

	List<SalaResponse> toResponse(List<SalaEntity> salaEntity);

	@Mapping(target = "filme", ignore = true)
	SalaEntity toEntity(SalaDto salaDto);
}
