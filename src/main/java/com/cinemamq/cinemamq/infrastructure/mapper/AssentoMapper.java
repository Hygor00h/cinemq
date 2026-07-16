package com.cinemamq.cinemamq.infrastructure.mapper;


import com.cinemamq.cinemamq.infrastructure.model.dto.AssentoDto;
import com.cinemamq.cinemamq.infrastructure.model.entity.AssentoEntity;
import org.mapstruct.Mapper;


import java.util.Set;

@Mapper
public interface AssentoMapper {

	AssentoDto toDto(AssentoEntity entity);
	AssentoEntity toEntity(AssentoDto dto);


	Set<AssentoDto> toDtoSet(Set<AssentoEntity> entities);
	Set<AssentoEntity> toEntitySet(Set<AssentoDto> dtos);
}
