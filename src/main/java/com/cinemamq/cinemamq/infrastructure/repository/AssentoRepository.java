package com.cinemamq.cinemamq.infrastructure.repository;

import com.cinemamq.cinemamq.infrastructure.model.entity.AssentoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface AssentoRepository extends JpaRepository<AssentoEntity, Long> {

//	@Query(value = "SELECT * FROM public.assentos")
//	List<AssentoEntity>findByFilmeIdAndOcupadoFalse(UUID filmeId);

	List<AssentoEntity> findBySalaIdAndOcupadoFalse(UUID salaId);

//	@Query("SELECT a FROM AssentoEntity a WHERE a.filme.id = :filmeId AND a.ocupado = false")
//	List<AssentoEntity> buscarAssentosDisponiveisPorFilme(@Param("filmeId") UUID filmeId);
}
