package com.sim.backend.material.dto;

import com.sim.backend.material.entity.MaterialCategory;
import com.sim.backend.material.entity.MaterialUnit;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;

public record MaterialRequest(

        @NotBlank(message = " El codigo es obligatorio")
        @Size(max = 30,message = "El codigo no puede superar los 30 caracteres")
        String code,

        @NotBlank(message = " El nombre es obligatorio")
        @Size(max = 150,message = "El nombre no puede superar los 150 caracteres")
        String name,

        @Size(max = 500,message = "La descripcion no puede superar los 500 caracteres")
        String description,

        @Size(max = 150,message = "El proveedor no puede superar los 150 caracteres")
        String supplier,

        @NotNull(message = "La categoria es obligatoria")
        MaterialCategory category,

        @DecimalMin(value  ="0.0", inclusive = true, message = "El peso no puede ser negativo")
        @Digits(integer = 10, fraction = 3, message = "El peso debe tener como maximo 10 caracteres")
        BigDecimal weight,


        @Size(max = 100,message = "Las medidas no pueden superar los 100 caracteres")
        String dimensions,

        @NotNull(message = "La unidad es obligatoria")
        MaterialUnit unit,

        @NotNull(message ="El precio unitario es obligatorio")
        @DecimalMin(value  ="0.0", inclusive = true, message = "El precio unitario  no puede ser negativo")
        @Digits(integer = 12, fraction = 2, message = "El precio debe tener como maximo 12 caracteres y 2 decimales")
        BigDecimal unitPrice,

        @NotNull(message ="El stock es obligatorio")
        @DecimalMin(value  ="0.0", inclusive = true, message = "El stock  no puede ser negativo")
        @Digits(integer = 12, fraction = 3, message = "El precio debe tener como maximo 12 caracteres y 3 decimales")
        BigDecimal stock,

        Boolean active
) {
}
