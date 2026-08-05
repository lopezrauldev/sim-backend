package com.sim.backend.material.dto;

import com.sim.backend.material.entity.MaterialCategory;
import com.sim.backend.material.entity.MaterialUnit;

import java.math.BigDecimal;
import java.util.UUID;

public record MaterialResponse(

        UUID id,
        String code,
        String name,
        String description,
        String supplier,
        MaterialCategory category,
        BigDecimal weight,
        String dimensions,
        MaterialUnit unit,
        BigDecimal unitPrice,
        BigDecimal stock,
        boolean active
) {
}
