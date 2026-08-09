package com.sim.backend.product.persistence.mapper;

import com.sim.backend.product.entity.Product;
import com.sim.backend.product.persistence.entity.ProductJpaEntity;

public class ProductPersistenceMapper {

    private ProductPersistenceMapper() {
    }
    public static ProductJpaEntity toJpaEntity(Product product) {
        return new ProductJpaEntity(
                product.getId(),
                product.getCode(),
                product.getName(),
                product.getDescription(),
                product.getUnit(),
                product.getUnitPrice(),
                product.isActive()
        );
    }
    public static Product toDomain(ProductJpaEntity entity) {
        return new Product(
                entity.getId(),
                entity.getCode(),
                entity.getName(),
                entity.getDescription(),
                entity.getUnit(),
                entity.getUnitPrice(),
                entity.isActive()
        );
    }
}
