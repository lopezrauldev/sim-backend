package com.sim.backend.quote.persistence.generator;

import com.sim.backend.quote.repository.QuoteNumberGenerator;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Component;

@Component
public class PostgresQuoteNumberGenerator implements QuoteNumberGenerator {

    private final EntityManager entityManager;

    public PostgresQuoteNumberGenerator(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public String nextNumber() {

        Number value = (Number) entityManager
                .createNativeQuery(
                        "SELECT nextval('quote_number_seq')"
                )
                .getSingleResult();

        return String.format(
                "COT-%06d",
                value.longValue()
        );
    }
}
