package com.sim.backend.product.dto;

import com.sim.backend.material.entity.MaterialUnit;

import java.math.BigDecimal;
import java.util.UUID;

public record ProductMaterialResponse(
        UUID id,
        UUID productId,
        UUID materialId,
        String materialCode,
        String materialName,
        MaterialUnit materialUnit,
        BigDecimal baseQuantity
) {
}
