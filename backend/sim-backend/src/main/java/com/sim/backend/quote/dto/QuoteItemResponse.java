package com.sim.backend.quote.dto;

import com.sim.backend.shared.kernel.Unit;

import java.math.BigDecimal;
import java.util.UUID;

public record QuoteItemResponse(

        UUID id,
        UUID productId,
        String description,
        Unit unit,
        BigDecimal quantity,
        BigDecimal unitPrice,
        BigDecimal subtotal

) {
}
