package com.sim.backend.quote.persistence.adapter;

import com.sim.backend.quote.entity.Quote;
import com.sim.backend.quote.persistence.entity.QuoteJpaEntity;
import com.sim.backend.quote.persistence.mapper.QuotePersistenceMapper;
import com.sim.backend.quote.persistence.repository.SpringDataQuoteRepository;
import com.sim.backend.quote.repository.QuoteRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class QuoteRepositoryAdapter implements QuoteRepository {

    private final SpringDataQuoteRepository repository;

    public QuoteRepositoryAdapter(
            SpringDataQuoteRepository repository
    ) {
        this.repository = repository;
    }

    @Override
    public Quote save(Quote quote) {

        Optional<QuoteJpaEntity> existingEntity =
                repository.findById(quote.getId());

        QuoteJpaEntity entity;

        if (existingEntity.isPresent()) {

            entity = existingEntity.get();

            QuotePersistenceMapper.updateJpaEntity(
                    quote,
                    entity
            );

        } else {

            entity = QuotePersistenceMapper.toJpaEntity(
                    quote
            );
        }

        QuoteJpaEntity savedEntity =
                repository.save(entity);

        return QuotePersistenceMapper.toDomain(savedEntity);

    }

    @Override
    public Optional<Quote> findById(UUID id) {

        return repository.findById(id)
                .map(QuotePersistenceMapper::toDomain);
    }

    @Override
    public Optional<Quote> findByNumber(String number) {

        return repository.findByNumber(number)
                .map(QuotePersistenceMapper::toDomain);
    }

    @Override
    public List<Quote> findAll() {

        return repository.findAll()
                .stream()
                .map(QuotePersistenceMapper::toDomain)
                .toList();
    }

    @Override
    public boolean existsByNumber(String number) {
        return repository.existsByNumber(number);
    }
}
