package com.sim.backend.material.entity;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.UUID;

public class Material {

    private final UUID id;

    private String code;
    private String name;
    private String description;
    private String supplier;
    private MaterialCategory category;
    private BigDecimal weight;
    private String dimensions;
    private MaterialUnit unit;
    private BigDecimal unitPrice;
    private BigDecimal stock;
    private boolean active;

    public Material(
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
        this.id = Objects.requireNonNull(id, "El id es obligatorio");
        setCode(code);
        setName(name);
        setDescription(description);
        setSupplier(supplier);
        setCategory(category);
        setWeight(weight);
        setDimensions(dimensions);
        setUnit(unit);
        setUnitPrice(unitPrice);
        setStock(stock);
        this.active = active;
    }

    public UUID getId() {
        return id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = requireText(code, " El codigo es obligatorio");

        if (this.code.length() > 30) {
            throw new IllegalArgumentException("El codigo no puede superar los 30 caracteres");
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = requireText(name, " El nombre es obligatorio");

        if (this.name.length() > 150) {
            throw new IllegalArgumentException("El nombre no puede superar los 150 caracteres");
        }
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = normalize(description);

        if (this.description != null && this.description.length() > 500) {
            throw new IllegalArgumentException("La descripcion no puede superar los 500 caracteres");
        }
    }

    public String getSupplier() {
        return supplier;
    }

    public void setSupplier(String supplier) {
        this.supplier = normalize(supplier);

        if (this.supplier != null && this.supplier.length() > 150) {
            throw new IllegalArgumentException("El proveedor no puede superar los 150 caracteres");
        }
    }

    public MaterialCategory getCategory() {
        return category;
    }

    public void setCategory(MaterialCategory category) {
        this.category = Objects.requireNonNull(category, "La categoria es obligatoria");
    }

    public BigDecimal getWeight() {
        return weight;
    }

    public void setWeight(BigDecimal weight) {
        if (weight != null && weight.signum() < 0) {
            throw new IllegalArgumentException("El peso no puede ser negativo");
        }
        this.weight = weight;
    }

    public String getDimensions() {
        return dimensions;
    }

    public void setDimensions(String dimensions) {
        this.dimensions = normalize(dimensions);

        if (this.dimensions != null && this.dimensions.length()>100) {
        throw new IllegalArgumentException("Las medidas no pueden superar los 100 caracteres");
        }
    }

    public MaterialUnit getUnit() {
        return unit;
    }

    public void setUnit(MaterialUnit unit) {
        this.unit = Objects.requireNonNull(unit, "La unidad es obligatoria");
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        Objects.requireNonNull(unitPrice, "El precio unitario es obligatorio");

        if (unitPrice.signum() < 0) {
            throw new IllegalArgumentException("El precio unitario no pueede ser negativo");
        }
        this.unitPrice = unitPrice;
    }

    public BigDecimal getStock() {
        return stock;
    }

    public void setStock(BigDecimal stock) {
        Objects.requireNonNull(stock, "El stock es obligatorio");

        if (stock.signum() < 0) {
            throw new IllegalArgumentException("El stock no pueede ser negativo");
        }
        this.stock = stock;
    }

    public boolean isActive() {
        return active;
    }

    public void activate() {
        this.active = true;
    }

    public void deactivate() {
        this.active = false;
    }

    private static String requireText(String value, String message) {
        String normalized = normalize(value);

        if (normalized == null) {
            throw new IllegalArgumentException(message);
        }
        return normalized;
    }

    private static String normalize(String value) {
        if (value ==null) {
            return null;
        }

        String normalized = value.trim();

        return normalized.isEmpty() ? null : normalized;
    }

}






