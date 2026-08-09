package com.sim.backend.product.entity;

import com.sim.backend.shared.kernel.Unit;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.UUID;

public class Product {

    private final UUID id;
    private String code;
    private String name;
    private String description;
    private Unit unit;
    private BigDecimal unitPrice;
    private boolean active;

    public Product(
            UUID id,
            String code,
            String name,
            String description,
            Unit unit,
            BigDecimal unitPrice,
            boolean active
    ) {
        this.id = Objects.requireNonNull(id, "El id es obligatorio");
        this.code = requireText(code, "El código es obligatorio");
        this.name = requireText(name, "El nombre es obligatorio");
        this.description = normalize(description);
        this.unit = Objects.requireNonNull(unit, "La unidad es obligatoria");
        this.unitPrice = validatePrice(unitPrice);
        this.active = active;
    }

    public static Product create(
            String code,
            String name,
            String description,
            Unit unit,
            BigDecimal unitPrice
    ) {
        return new Product(
                UUID.randomUUID(),
                code,
                name,
                description,
                unit,
                unitPrice,
                true
        );
    }

    public void update(
            String code,
            String name,
            String description,
            Unit unit,
            BigDecimal unitPrice
    ) {
        this.code = requireText(code, "El código es obligatorio");
        this.name = requireText(name, "El nombre es obligatorio");
        this.description = normalize(description);
        this.unit = Objects.requireNonNull(unit, "La unidad es obligatoria");
        this.unitPrice = validatePrice(unitPrice);
    }

    public void activate() {
        this.active = true;
    }

    public void deactivate() {
        this.active = false;
    }

    private static String requireText(String value, String message) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException(message);
        }

        return value.trim();
    }

    private static String normalize(String value) {
        return value == null ? null : value.trim();
    }

    private static BigDecimal validatePrice(BigDecimal price) {
        Objects.requireNonNull(price, "El precio unitario es obligatorio");

        if (price.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException(
                    "El precio unitario no puede ser negativo"
            );
        }

        return price;
    }

    public UUID getId() {
        return id;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Unit getUnit() {
        return unit;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public boolean isActive() {
        return active;
    }
}

