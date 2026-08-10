package com.sim.backend.product.persistence.mapper;

import com.sim.backend.product.entity.ProductMaterial;
import com.sim.backend.product.persistence.entity.ProductMaterialJpaEntity;

public final class ProductMaterialPersistenceMapper {

    private ProductMaterialPersistenceMapper() {
    }

    public static ProductMaterialJpaEntity toJpaEntity(
            ProductMaterial productMaterial
    ) {
        return new ProductMaterialJpaEntity(
                productMaterial.getId(),
                productMaterial.getProductId(),
                productMaterial.getMaterialId(),
                productMaterial.getBaseQuantity()
        );
    }

    public static ProductMaterial toDomain(
            ProductMaterialJpaEntity entity
    ) {
        return new ProductMaterial(
                entity.getId(),
                entity.getProductId(),
                entity.getMaterialId(),
                entity.getBaseQuantity()
        );
    }
}
