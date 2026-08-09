package com.sim.backend.product.dto;

import com.sim.backend.shared.kernel.Unit;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

public record ProductRequest(

        @NotBlank(message = "El código es obligatorio")
        @Size(max = 30, message = "El código no puede superar los 30 caracteres")
        String code,

        @NotBlank(message = "El nombre es obligatorio")
        @Size(max = 150, message = "El nombre no puede superar los 150 caracteres")
        String name,

        @Size(max = 300, message = "La descripción no puede superar los 300 caracteres")
        String description,

        @NotNull(message = "La unidad es obligatoria")
        Unit unit,

        @NotNull(message = "El precio unitario es obligatorio")
        @DecimalMin(value = "0.0", inclusive = true,
                message = "El precio unitario no puede ser negativo")
        BigDecimal unitPrice
) {
}
