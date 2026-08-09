package com.sim.backend.product.persistence.entity;


import com.sim.backend.shared.kernel.Unit;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "products")
public class ProductJpaEntity {

    @Id
    private UUID id;

    @Column(nullable = false, unique = true, length = 30)
    private String code;

    @Column(nullable = false, length = 150)
    private String name;

    @Column(length = 300)
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private Unit unit;

    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal unitPrice;

    @Column(nullable = false)
    private boolean active;

    protected ProductJpaEntity() {
    }

    public ProductJpaEntity(
            UUID id,
            String code,
            String name,
            String description,
            Unit unit,
            BigDecimal unitPrice,
            boolean active
    ) {
        this.id = id;
        this.code = code;
        this.name = name;
        this.description = description;
        this.unit = unit;
        this.unitPrice = unitPrice;
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