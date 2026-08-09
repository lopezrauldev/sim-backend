package com.sim.backend.product.dto;

import com.sim.backend.shared.kernel.Unit;

import java.math.BigDecimal;
import java.util.UUID;

public record ProductResponse(

        UUID id,
        String code,
        String name,
        String description,
        Unit unit,
        BigDecimal unitPrice,
        boolean active
) {
}
