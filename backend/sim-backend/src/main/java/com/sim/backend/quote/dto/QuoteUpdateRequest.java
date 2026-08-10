package com.sim.backend.quote.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;

import java.util.List;

public record QuoteUpdateRequest(
        @NotEmpty(message = "La cotización debe tener al menos un item")
        List<@Valid QuoteItemRequest> items
) {
}
