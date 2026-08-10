package com.sim.backend.product.repository;

import com.sim.backend.product.entity.ProductMaterial;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ProductMaterialRepository {

    ProductMaterial save(ProductMaterial productMaterial);

    Optional<ProductMaterial> findById(UUID id);

    List<ProductMaterial> findByProductId(UUID productId);

    boolean existsByProductIdAndMaterialId(
            UUID productId,
            UUID materialId
    );

    void deleteById(UUID id);
}
