package com.sim.backend.quote.entity;

import com.sim.backend.shared.kernel.Unit;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.UUID;

public class QuoteItem {

    private final UUID id;
    private final UUID productId;
    private String description;
    private Unit unit;
    private BigDecimal quantity;
    private BigDecimal unitPrice;

    public QuoteItem(
            UUID id,
            UUID productId,
            String description,
            Unit unit,
            BigDecimal quantity,
            BigDecimal unitPrice
    ) {
        this.id = Objects.requireNonNull(id, "El id es obligatorio");
        this.productId = Objects.requireNonNull(
                productId,
                "El producto es obligatorio"
        );
        this.description = requireText(
                description,
                "La descripción es obligatoria"
        );
        this.unit = Objects.requireNonNull(
                unit,
                "La unidad es obligatoria"
        );
        this.quantity = validateQuantity(quantity);
        this.unitPrice = validateUnitPrice(unitPrice);
    }

    public static QuoteItem create(
            UUID productId,
            String description,
            Unit unit,
            BigDecimal quantity,
            BigDecimal unitPrice
    ) {
        return new QuoteItem(
                UUID.randomUUID(),
                productId,
                description,
                unit,
                quantity,
                unitPrice
        );
    }

    public void changeDescription(String description) {
        this.description = requireText(
                description,
                "La descripción es obligatoria"
        );
    }

    public void changeUnit(Unit unit) {
        this.unit = Objects.requireNonNull(
                unit,
                "La unidad es obligatoria"
        );
    }

    public void changeQuantity(BigDecimal quantity) {
        this.quantity = validateQuantity(quantity);
    }

    public void changeUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = validateUnitPrice(unitPrice);
    }

    public BigDecimal getSubtotal() {
        return quantity.multiply(unitPrice);
    }

    private static String requireText(String value, String message) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException(message);
        }

        return value.trim();
    }

    private static BigDecimal validateQuantity(BigDecimal quantity) {
        Objects.requireNonNull(
                quantity,
                "La cantidad es obligatoria"
        );

        if (quantity.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException(
                    "La cantidad debe ser mayor que cero"
            );
        }

        return quantity;
    }

    private static BigDecimal validateUnitPrice(BigDecimal unitPrice) {
        Objects.requireNonNull(
                unitPrice,
                "El precio unitario es obligatorio"
        );

        if (unitPrice.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException(
                    "El precio unitario no puede ser negativo"
            );
        }

        return unitPrice;
    }

    public UUID getId() {
        return id;
    }

    public UUID getProductId() {
        return productId;
    }

    public String getDescription() {
        return description;
    }

    public Unit getUnit() {
        return unit;
    }

    public BigDecimal getQuantity() {
        return quantity;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }
}
