package com.sim.backend.product.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record ProductMaterialQuantityRequest(

        @NotNull(message = "La cantidad base es obligatoria")
        @DecimalMin(
                value = "0.0",
                inclusive = false,
                message = "La cantidad base debe ser mayor que cero"
        )
        @Digits(
                integer = 10,
                fraction = 3,
                message = "La cantidad base debe tener máximo 10 enteros y 3 decimales"
        )
        BigDecimal baseQuantity

) {
}
