package com.sim.backend.quote.entity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class Quote {

    private static final BigDecimal GG_PROFIT_PERCENTAGE =
            new BigDecimal("0.10");

    private final UUID id;
    private final String number;
    private final UUID clientId;
    private final LocalDate date;
    private final List<QuoteItem> items;
    private QuoteStatus status;

    public Quote(
            UUID id,
            String number,
            UUID clientId,
            LocalDate date,
            List<QuoteItem> items,
            QuoteStatus status
    ) {
        this.id = Objects.requireNonNull(id, "El id es obligatorio");
        this.number = requireText(
                number,
                "El número de cotización es obligatorio"
        );
        this.clientId = Objects.requireNonNull(
                clientId,
                "El cliente es obligatorio"
        );
        this.date = Objects.requireNonNull(
                date,
                "La fecha es obligatoria"
        );
        this.items = new ArrayList<>(
                Objects.requireNonNull(items, "Los items son obligatorios")
        );
        this.status = Objects.requireNonNull(
                status,
                "El estado es obligatorio"
        );
    }

    public static Quote create(
            String number,
            UUID clientId
    ) {
        return new Quote(
                UUID.randomUUID(),
                number,
                clientId,
                LocalDate.now(),
                new ArrayList<>(),
                QuoteStatus.SAVED
        );
    }

    public void addItem(QuoteItem item) {
        Objects.requireNonNull(item, "El item es obligatorio");

        items.add(item);
    }

    public BigDecimal getSubtotal() {
        return items.stream()
                .map(QuoteItem::getSubtotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public BigDecimal getGeneralExpensesAndProfit() {
        return getSubtotal()
                .multiply(GG_PROFIT_PERCENTAGE);
    }

    public BigDecimal getTotal() {
        return getSubtotal()
                .add(getGeneralExpensesAndProfit());
    }

    public void cancel() {
        this.status = QuoteStatus.CANCELED;
    }

    private static String requireText(String value, String message) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException(message);
        }

        return value.trim();
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

    public List<QuoteItem> getItems() {
        return Collections.unmodifiableList(items);
    }

    public QuoteStatus getStatus() {
        return status;
    }
}
