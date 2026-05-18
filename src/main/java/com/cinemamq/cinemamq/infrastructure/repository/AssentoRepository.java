package com.cinemamq.cinemamq.infrastructure.repository;

import com.cinemamq.cinemamq.infrastructure.model.entity.AssentoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface AssentoRepository extends JpaRepository<AssentoEntity, UUID> {
}
