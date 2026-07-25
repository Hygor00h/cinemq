package com.cinemamq.cinemamq.infrastructure.repository;

import com.cinemamq.cinemamq.infrastructure.model.entity.AssentoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface AssentoRepository extends JpaRepository<AssentoEntity, UUID> {

	List<AssentoEntity> findBySalaIdAndOcupadoFalse(UUID salaId);

}
