package com.cinemamq.cinemamq.infrastructure.repository;

import com.cinemamq.cinemamq.infrastructure.model.entity.SalaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface SalaRepository extends JpaRepository<SalaEntity, Long> {

	List<SalaEntity> findByFilmeId(UUID filmeId);

	//1 DISTINCT depois do SELECT para resolver a multiplicação no json ou usar o SET na entity no logar do LIST
	@Query("SELECT DISTINCT s FROM SalaEntity s WHERE s.filme.id = :filmeId")
	List<SalaEntity> buscarSalasPorFilmeIdCustom(@Param("filmeId") UUID filmeId);

	@Query("SELECT s FROM SalaEntity s LEFT JOIN FETCH s.assentos WHERE s.id = :salaId")
	Optional<SalaEntity> buscarSalaComAssentos(@Param("salaId") UUID salaId);

	// Native Query: O mesmo SQL que você rodou no pgAdmin
//	@Query(value = "SELECT s.* FROM public.salas s " +
//					"INNER JOIN public.filmes f ON s.filme_id = f.id " +
//					"WHERE f.id = :filmeId", nativeQuery = true)
//	List<SalaEntity> buscarSalasPorFilmeIdNative(@Param("filmeId") UUID filmeId);
}
