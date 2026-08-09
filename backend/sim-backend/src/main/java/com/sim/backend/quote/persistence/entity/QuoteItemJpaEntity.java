package com.sim.backend.quote.persistence.entity;

import com.sim.backend.shared.kernel.Unit;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "quote_items")
public class QuoteItemJpaEntity {

    @Id
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "quote_id", nullable = false)
    private QuoteJpaEntity quote;

    @Column(nullable = false)
    private UUID productId;

    @Column(nullable = false, length = 300)
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private Unit unit;

    @Column(nullable = false, precision = 12, scale = 3)
    private BigDecimal quantity;

    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal unitPrice;

    protected QuoteItemJpaEntity() {
    }

    public QuoteItemJpaEntity(
            UUID id,
            UUID productId,
            String description,
            Unit unit,
            BigDecimal quantity,
            BigDecimal unitPrice
    ) {
        this.id = id;
        this.productId = productId;
        this.description = description;
        this.unit = unit;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
    }

    public void setQuote(QuoteJpaEntity quote) {
        this.quote = quote;
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
