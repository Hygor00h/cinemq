package com.cinemamq.cinemamq.infrastructure.repository;

import com.cinemamq.cinemamq.infrastructure.model.entity.SalaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Repository
public interface SalaRepository extends JpaRepository<SalaEntity, UUID> {


	@Query("SELECT s FROM SalaEntity s WHERE s.filme.id = :filmeId")
	List<SalaEntity> buscarSalasPorFilmeIdCustom(@Param("filmeId") UUID filmeId);

	@Query("SELECT s FROM SalaEntity s LEFT JOIN FETCH s.assentos a WHERE s.id = :salaId ORDER BY a.numero ASC")
	Optional<SalaEntity> buscarSalaComAssentos(@Param("salaId") UUID salaId);

}
