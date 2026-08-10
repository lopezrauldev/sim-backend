package com.sim.backend.quote.persistence.entity;

import com.sim.backend.quote.entity.QuoteStatus;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "quotes")
public class QuoteJpaEntity {

    @Id
    private UUID id;

    @Column(nullable = false, unique = true, length = 30)
    private String number;

    @Column(nullable = false)
    private UUID clientId;

    @Column(nullable = false)
    private LocalDate date;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private QuoteStatus status;

    @OneToMany(
            mappedBy = "quote",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<QuoteItemJpaEntity> items = new ArrayList<>();

    protected QuoteJpaEntity() {
    }

    public QuoteJpaEntity(
            UUID id,
            String number,
            UUID clientId,
            LocalDate date,
            QuoteStatus status
    ) {
        this.id = id;
        this.number = number;
        this.clientId = clientId;
        this.date = date;
        this.status = status;
    }

    public void addItem(QuoteItemJpaEntity item) {
        items.add(item);
        item.setQuote(this);
    }

    public void replaceItems(List<QuoteItemJpaEntity> newItems) {
        items.clear();

        for (QuoteItemJpaEntity item : newItems) {
            addItem(item);
        }
    }

    public void changeStatus(QuoteStatus status) {
        this.status = status;
    }

    public UUID getId() {
        return id;
    }

    public String getNumber() {
        return number;
    }

    public UUID getClientId() {
        return clientId;
    }

    public LocalDate getDate() {
        return date;
    }

    public QuoteStatus getStatus() {
        return status;
    }

    public List<QuoteItemJpaEntity> getItems() {
        return items;
    }
}