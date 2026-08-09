package com.sim.backend.quote.persistence.repository;

import com.sim.backend.quote.persistence.entity.QuoteJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface SpringDataQuoteRepository  extends JpaRepository<QuoteJpaEntity, UUID> {

    Optional<QuoteJpaEntity> findByNumber(String number);

    boolean existsByNumber(String number);
}
