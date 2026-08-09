package com.sim.backend.product.persistence.repository;

import com.sim.backend.product.persistence.entity.ProductJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface SpringDataProductRepository
        extends JpaRepository<ProductJpaEntity, UUID> {

    Optional<ProductJpaEntity> findByCode(String code);

    boolean existsByCode(String code);
}
