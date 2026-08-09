package com.sim.backend.quote.repository;

import com.sim.backend.quote.entity.Quote;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface QuoteRepository {

    Quote save(Quote quote);

    Optional<Quote> findById(UUID id);

    Optional<Quote> findByNumber(String number);

    List<Quote> findAll();

    boolean existsByNumber(String number);
}
