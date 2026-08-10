package com.sim.backend.product.entity;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.UUID;

public class ProductMaterial {

    private final UUID id;
    private final UUID productId;
    private final UUID materialId;
    private BigDecimal baseQuantity;

    public ProductMaterial(
            UUID id,
            UUID productId,
            UUID materialId,
            BigDecimal baseQuantity
    ) {
        this.id = Objects.requireNonNull(id, "El id es obligatorio");
        this.productId = Objects.requireNonNull(productId, "El productId es obligatorio");
        this.materialId = Objects.requireNonNull(materialId, "El materialId es obligatorio");

        validateBaseQuantity(baseQuantity);

        this.baseQuantity = baseQuantity;
    }

    public UUID getId() {
        return id;
    }

    public UUID getProductId() {
        return productId;
    }

    public UUID getMaterialId() {
        return materialId;
    }

    public BigDecimal getBaseQuantity() {
        return baseQuantity;
    }

    public void updateBaseQuantity(BigDecimal baseQuantity) {
        validateBaseQuantity(baseQuantity);
        this.baseQuantity = baseQuantity;
    }

    private void validateBaseQuantity(BigDecimal baseQuantity) {

        Objects.requireNonNull(
                baseQuantity,
                "La cantidad base es obligatoria"
        );

        if (baseQuantity.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException(
                    "La cantidad base debe ser mayor que cero"
            );
        }
    }
}
