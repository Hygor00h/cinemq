package com.cinemamq.cinemamq.infrastructure.repository;

import com.cinemamq.cinemamq.infrastructure.model.entity.SalaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface SalaRepository extends JpaRepository<SalaEntity, Long> {

	List<SalaEntity> findByFilmeId(UUID filmeId);

	@Query("SELECT s FROM SalaEntity s WHERE s.filme.id = :filmeId")
	List<SalaEntity> buscarSalasPorFilmeIdCustom(@Param("filmeId") UUID filmeId);


	// Native Query: O mesmo SQL que você rodou no pgAdmin
//	@Query(value = "SELECT s.* FROM public.salas s " +
//					"INNER JOIN public.filmes f ON s.filme_id = f.id " +
//					"WHERE f.id = :filmeId", nativeQuery = true)
//	List<SalaEntity> buscarSalasPorFilmeIdNative(@Param("filmeId") UUID filmeId);
}
