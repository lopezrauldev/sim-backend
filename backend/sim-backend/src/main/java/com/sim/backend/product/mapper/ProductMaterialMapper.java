package com.sim.backend.product.mapper;

import com.sim.backend.material.entity.Material;
import com.sim.backend.product.dto.ProductMaterialRequest;
import com.sim.backend.product.dto.ProductMaterialResponse;
import com.sim.backend.product.entity.ProductMaterial;

import java.util.UUID;

public final class ProductMaterialMapper {

    private ProductMaterialMapper() {}

    public static ProductMaterial toDomain(UUID productId, ProductMaterialRequest request){
        return new ProductMaterial(
                UUID.randomUUID(),
                productId,
                request.materialId(),
                request.baseQuantity()
        );
    }

    public static ProductMaterialResponse toResponse(ProductMaterial productMaterial, Material material) {
        return new ProductMaterialResponse(
                productMaterial.getId(),
                productMaterial.getProductId(),
                productMaterial.getMaterialId(),
                material.getCode(),
                material.getName(),
                material.getUnit(),
                productMaterial.getBaseQuantity()
        );
    }
}
