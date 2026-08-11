package com.sim.backend.quote.dto;

import com.sim.backend.material.entity.MaterialUnit;

import java.math.BigDecimal;
import java.util.UUID;

public record QuoteMaterialResponse(

        UUID materialId,
        String materialCode,
        String materialName,
        MaterialUnit materialUnit,
        BigDecimal requiredQuantity
) {
}
