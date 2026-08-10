package com.sim.backend.quote.persistence.mapper;

import com.sim.backend.quote.entity.Quote;
import com.sim.backend.quote.entity.QuoteItem;
import com.sim.backend.quote.persistence.entity.QuoteItemJpaEntity;
import com.sim.backend.quote.persistence.entity.QuoteJpaEntity;

import java.util.List;

public final class QuotePersistenceMapper {

    private QuotePersistenceMapper() {
    }

    public static QuoteJpaEntity toJpaEntity(Quote quote) {

        QuoteJpaEntity entity = new QuoteJpaEntity(
                quote.getId(),
                quote.getNumber(),
                quote.getClientId(),
                quote.getDate(),
                quote.getStatus()
        );

        quote.getItems()
                .stream()
                .map(QuotePersistenceMapper::toItemJpaEntity)
                .forEach(entity::addItem);

        return entity;
    }

    private static QuoteItemJpaEntity toItemJpaEntity(QuoteItem item) {

        return new QuoteItemJpaEntity(
                item.getId(),
                item.getProductId(),
                item.getDescription(),
                item.getUnit(),
                item.getQuantity(),
                item.getUnitPrice()
        );
    }

    public static Quote toDomain(QuoteJpaEntity entity) {

        List<QuoteItem> items = entity.getItems()
                .stream()
                .map(QuotePersistenceMapper::toItemDomain)
                .toList();

        return new Quote(
                entity.getId(),
                entity.getNumber(),
                entity.getClientId(),
                entity.getDate(),
                items,
                entity.getStatus()
        );
    }

    private static QuoteItem toItemDomain(QuoteItemJpaEntity entity) {

        return new QuoteItem(
                entity.getId(),
                entity.getProductId(),
                entity.getDescription(),
                entity.getUnit(),
                entity.getQuantity(),
                entity.getUnitPrice()
        );
    }

    public static void updateJpaEntity(
            Quote quote,
            QuoteJpaEntity entity
    ) {
        entity.changeStatus(quote.getStatus());

        List<QuoteItemJpaEntity> items = quote.getItems()
                .stream()
                .map(QuotePersistenceMapper::toItemJpaEntity)
                .toList();

        entity.replaceItems(items);
    }
}
