package com.sim.backend.quote.controller;

import com.sim.backend.quote.dto.QuoteRequest;
import com.sim.backend.quote.dto.QuoteResponse;
import com.sim.backend.quote.service.QuoteService;
import com.sim.backend.quote.dto.QuoteMaterialResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.sim.backend.quote.dto.QuoteUpdateRequest;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/quotes")
public class QuoteController {

    private final QuoteService quoteService;

    public QuoteController(QuoteService quoteService) {
        this.quoteService = quoteService;
    }

    @GetMapping("/{id}/materials")
    public ResponseEntity<List<QuoteMaterialResponse>> getRequiredMaterials(
            @PathVariable UUID id
    ) {

        return ResponseEntity.ok(
                quoteService.getRequiredMaterials(id)
        );
    }

    @PostMapping
    public ResponseEntity<QuoteResponse> create(
            @Valid @RequestBody QuoteRequest request
    ) {
        QuoteResponse response = quoteService.create(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<QuoteResponse> findById(
            @PathVariable UUID id
    ) {
        return ResponseEntity.ok(
                quoteService.findById(id)
        );
    }

    @GetMapping
    public ResponseEntity<List<QuoteResponse>> findAll() {
        return ResponseEntity.ok(
                quoteService.findAll()
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<QuoteResponse> update(
            @PathVariable UUID id,
            @Valid @RequestBody QuoteUpdateRequest request
    ) {
        return ResponseEntity.ok(
                quoteService.update(id, request)
        );
    }

    @PatchMapping("/{id}/cancel")
    public ResponseEntity<QuoteResponse> cancel(
            @PathVariable UUID id
    ) {
        return ResponseEntity.ok(
                quoteService.cancel(id)
        );
    }
}
