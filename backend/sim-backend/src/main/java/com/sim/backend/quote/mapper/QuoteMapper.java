package com.sim.backend.quote.mapper;

import com.sim.backend.quote.dto.QuoteItemResponse;
import com.sim.backend.quote.dto.QuoteResponse;
import com.sim.backend.quote.entity.Quote;
import com.sim.backend.quote.entity.QuoteItem;

public final class QuoteMapper {

    private QuoteMapper() {
    }

    public static QuoteItemResponse toItemResponse(QuoteItem item) {
        return new QuoteItemResponse(
                item.getId(),
                item.getProductId(),
                item.getDescription(),
                item.getUnit(),
                item.getQuantity(),
                item.getUnitPrice(),
                item.getSubtotal()
        );
    }

    public static QuoteResponse toResponse(Quote quote) {
        return new QuoteResponse(
                quote.getId(),
                quote.getNumber(),
                quote.getClientId(),
                quote.getDate(),
                quote.getItems()
                        .stream()
                        .map(QuoteMapper::toItemResponse)
                        .toList(),
                quote.getSubtotal(),
                quote.getGeneralExpensesAndProfit(),
                quote.getTotal(),
                quote.getStatus()
        );
    }
}
