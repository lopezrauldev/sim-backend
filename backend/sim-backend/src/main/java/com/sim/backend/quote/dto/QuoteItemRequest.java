package com.sim.backend.quote.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.util.UUID;

public record QuoteItemRequest(
        @NotNull(message = "El producto es obligatorio")
        UUID productId,

        @NotNull(message = "La cantidad es obligatoria")
        @DecimalMin(
                value = "0.01",
                message = "La cantidad debe ser mayor que cero"
        )
        BigDecimal quantity
) {
}
