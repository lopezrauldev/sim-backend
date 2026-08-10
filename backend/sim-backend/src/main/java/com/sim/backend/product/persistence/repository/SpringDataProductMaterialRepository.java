package com.sim.backend.product.persistence.repository;

import com.sim.backend.product.persistence.entity.ProductMaterialJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface SpringDataProductMaterialRepository extends JpaRepository<ProductMaterialJpaEntity, UUID> {

    List<ProductMaterialJpaEntity>  findByProductId(UUID productId);

    boolean existsByProductIdAndMaterialId(
            UUID productId,
            UUID materialId
    );
}
