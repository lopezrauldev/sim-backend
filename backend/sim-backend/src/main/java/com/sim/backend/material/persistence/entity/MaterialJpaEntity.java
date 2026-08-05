package com.sim.backend.material.persistence.entity;

import com.sim.backend.material.entity.MaterialCategory;
import com.sim.backend.material.entity.MaterialUnit;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "materials")
public class MaterialJpaEntity {

    @Id
    private UUID id;

    @Column(nullable = false, unique = true, length = 30)
    private String code;

    @Column(nullable = false, length = 150)
    private String name;

    @Column(length = 500)
    private String description;

    @Column(length = 150)
    private String supplier;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 50)
    private MaterialCategory category;

    @Column(precision = 13, scale = 3)
    private BigDecimal weight;

    @Column(length = 100)
    private String dimensions;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 50)
    private MaterialUnit unit;

    @Column(name = "unit_price", nullable = false, precision = 14, scale = 2)
    private BigDecimal unitPrice;

    @Column(nullable = false, precision = 15, scale = 3)
    private BigDecimal stock;

    @Column(nullable = false)
    private boolean active;

    protected MaterialJpaEntity() {

    }

    public MaterialJpaEntity(
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
    ){
        this.id = id;
        this.code = code;
        this.name = name;
        this.description = description;
        this.supplier = supplier;
        this.category = category;
        this.weight = weight;
        this.dimensions = dimensions;
        this.unit =unit;
        this.unitPrice = unitPrice;
        this.stock = stock;
        this.active = active;
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

    public String getSupplier() {
        return supplier;
    }

    public MaterialCategory getCategory() {
        return category;
    }

    public BigDecimal getWeight() {
        return weight;
    }

    public String getDimensions() {
        return dimensions;
    }

    public MaterialUnit getUnit() {
        return unit;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public BigDecimal getStock() {
        return stock;
    }

    public boolean isActive() {
        return active;
    }
}













