package com.sim.backend.product.service;

import com.sim.backend.product.dto.ProductMaterialQuantityRequest;
import com.sim.backend.product.dto.ProductMaterialRequest;
import com.sim.backend.product.dto.ProductMaterialResponse;

import java.util.List;
import java.util.UUID;

public interface ProductMaterialService {

    ProductMaterialResponse addMaterial(
            UUID productId,
            ProductMaterialRequest request
    );

    List<ProductMaterialResponse> findByProductId(UUID productId);

    ProductMaterialResponse updateBaseQuantity(
            UUID productId,
            UUID productMaterialId,
            ProductMaterialQuantityRequest request
    );

    void removeMaterial(
            UUID productId,
            UUID productMaterialId
    );
}
