package com.cinemamq.cinemamq.infrastructure.repository;

import com.cinemamq.cinemamq.infrastructure.model.entity.CompraEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface CompraRepository extends JpaRepository<CompraEntity, UUID> {


	@Query("SELECT COUNT(c) > 0 FROM CompraEntity c JOIN c.assento a WHERE a.id = :assentoId AND c.status = :status")
	Boolean existsByPedidoId(@Param("assentoId") UUID assentoId, @Param("status") String status);

	boolean existsByAssento_IdAndStatus(UUID assentoId, String status);
}
