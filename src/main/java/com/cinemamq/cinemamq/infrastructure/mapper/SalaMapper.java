package com.cinemamq.cinemamq.infrastructure.mapper;

import com.cinemamq.cinemamq.infrastructure.model.dto.SalaDto;
import com.cinemamq.cinemamq.infrastructure.model.entity.SalaEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", uses = {AssentoMapper.class})
public interface SalaMapper {

	// 💡 2. CORREÇÃO DO TARGET: Mudamos de "SalaDto" para "filme", que é a propriedade que recebe o UUID
	// 💡 3. CORREÇÃO DO SOURCE: Como o parâmetro chama "salaEntity", buscamos o ID em "salaEntity.filme.id"
	@Mapping(target = "filme", source = "salaEntity.filme.id")
	SalaDto toDto(SalaEntity salaEntity);

	// O MapStruct vai gerar o loop deste método usando a regra do método de cima!
	List<SalaDto> toDtoList(List<SalaEntity> salas);

	// Inverso: Quando for salvar, ignora o objeto FilmeEntity completo, pois você associará pelo ID no Service
	@Mapping(target = "filme", ignore = true)
	SalaEntity toEntity(SalaDto salaDto);
}
