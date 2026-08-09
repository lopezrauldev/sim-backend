package com.sim.backend.quote.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;
import java.util.UUID;

public record QuoteRequest(

        @NotNull(message = "El cliente es obligatorio")
        UUID clientId,

        @NotEmpty(message = "La cotización debe tener al menos un item")
        List<@Valid QuoteItemRequest> items

) {
}
