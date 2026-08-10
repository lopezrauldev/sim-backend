package com.sim.backend.product.persistence.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(
        name = "product_materials",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_product_material",
                        columnNames = {"product_id", "material_id"}
                )
        }
)
public class ProductMaterialJpaEntity {

    @Id
    private UUID id;

    @Column(name = "product_id", nullable = false)
    private UUID productId;

    @Column(name = "material_id", nullable = false)
    private UUID materialId;

    @Column(
            name = "base_quantity",
            nullable = false,
            precision = 13,
            scale = 3
    )
    private BigDecimal baseQuantity;

    protected ProductMaterialJpaEntity() {
    }

    public ProductMaterialJpaEntity(
            UUID id,
            UUID productId,
            UUID materialId,
            BigDecimal baseQuantity
    ) {
        this.id = id;
        this.productId = productId;
        this.materialId = materialId;
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
}
