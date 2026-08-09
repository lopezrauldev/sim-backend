package com.sim.backend.product.mapper;

import com.sim.backend.product.dto.ProductRequest;
import com.sim.backend.product.dto.ProductResponse;
import com.sim.backend.product.entity.Product;

public final class ProductMapper {

    private ProductMapper() {
    }

    public static Product toDomain(ProductRequest request) {
        return Product.create(
                request.code(),
                request.name(),
                request.description(),
                request.unit(),
                request.unitPrice()
        );
    }

    public static ProductResponse toResponse(Product product) {
        return new ProductResponse(
                product.getId(),
                product.getCode(),
                product.getName(),
                product.getDescription(),
                product.getUnit(),
                product.getUnitPrice(),
                product.isActive()
        );
    }
}
