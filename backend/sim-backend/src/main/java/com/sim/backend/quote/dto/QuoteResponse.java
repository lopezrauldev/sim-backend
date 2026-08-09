package com.sim.backend.quote.dto;

import com.sim.backend.quote.entity.QuoteStatus;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public record QuoteResponse(

        UUID id,
        String number,
        UUID clientId,
        LocalDate date,
        List<QuoteItemResponse> items,
        BigDecimal subtotal,
        BigDecimal generalExpensesAndProfit,
        BigDecimal total,
        QuoteStatus status

) {
}