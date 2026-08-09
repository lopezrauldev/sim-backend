package com.sim.backend.quote.service;

import com.sim.backend.quote.dto.QuoteRequest;
import com.sim.backend.quote.dto.QuoteResponse;

import java.util.List;
import java.util.UUID;

public interface QuoteService {

    QuoteResponse create(QuoteRequest request);

    QuoteResponse findById(UUID id);

    List<QuoteResponse> findAll();

    QuoteResponse cancel(UUID id);
}