package com.sim.backend.quote.service;

import com.sim.backend.client.repository.ClientRepository;
import com.sim.backend.product.entity.Product;
import com.sim.backend.product.repository.ProductRepository;
import com.sim.backend.quote.dto.QuoteItemRequest;
import com.sim.backend.quote.dto.QuoteRequest;
import com.sim.backend.quote.dto.QuoteResponse;
import com.sim.backend.quote.entity.Quote;
import com.sim.backend.quote.entity.QuoteItem;
import com.sim.backend.quote.mapper.QuoteMapper;
import com.sim.backend.quote.repository.QuoteNumberGenerator;
import com.sim.backend.quote.repository.QuoteRepository;
import com.sim.backend.shared.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import com.sim.backend.quote.dto.QuoteUpdateRequest;
import java.util.ArrayList;

import java.util.List;
import java.util.UUID;

@Service
public class DefaultQuoteService implements QuoteService {

    private final QuoteRepository quoteRepository;
    private final ClientRepository clientRepository;
    private final ProductRepository productRepository;
    private final QuoteNumberGenerator quoteNumberGenerator;

    public DefaultQuoteService(
            QuoteRepository quoteRepository,
            ClientRepository clientRepository,
            ProductRepository productRepository,
            QuoteNumberGenerator quoteNumberGenerator
    ) {
        this.quoteRepository = quoteRepository;
        this.clientRepository = clientRepository;
        this.productRepository = productRepository;
        this.quoteNumberGenerator = quoteNumberGenerator;
    }

    @Override
    public QuoteResponse create(QuoteRequest request) {

        clientRepository.findById(request.clientId())
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Cliente no encontrado con id: "
                                        + request.clientId()
                        )
                );

        String number = quoteNumberGenerator.nextNumber();

        Quote quote = Quote.create(
                number,
                request.clientId()
        );

        for (QuoteItemRequest itemRequest : request.items()) {

            Product product = productRepository
                    .findById(itemRequest.productId())
                    .orElseThrow(() ->
                            new ResourceNotFoundException(
                                    "Producto no encontrado con id: "
                                            + itemRequest.productId()
                            )
                    );

            if (!product.isActive()) {
                throw new IllegalArgumentException(
                        "El producto está inactivo: " + product.getName()
                );
            }

            QuoteItem item = QuoteItem.create(
                    product.getId(),
                    product.getName(),
                    product.getUnit(),
                    itemRequest.quantity(),
                    product.getUnitPrice()
            );

            quote.addItem(item);
        }

        Quote savedQuote = quoteRepository.save(quote);

        return QuoteMapper.toResponse(savedQuote);
    }

    @Override
    public QuoteResponse findById(UUID id) {

        Quote quote = quoteRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Cotización no encontrada con id: " + id
                        )
                );

        return QuoteMapper.toResponse(quote);
    }

    @Override
    public List<QuoteResponse> findAll() {

        return quoteRepository.findAll()
                .stream()
                .map(QuoteMapper::toResponse)
                .toList();
    }

    @Override
    public QuoteResponse update(
            UUID id,
            QuoteUpdateRequest request
    ) {

        Quote quote = quoteRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Cotización no encontrada con id: " + id
                        )
                );

        List<QuoteItem> newItems = new ArrayList<>();

        for (QuoteItemRequest itemRequest : request.items()) {

            Product product = productRepository
                    .findById(itemRequest.productId())
                    .orElseThrow(() ->
                            new ResourceNotFoundException(
                                    "Producto no encontrado con id: "
                                            + itemRequest.productId()
                            )
                    );

            if (!product.isActive()) {
                throw new IllegalArgumentException(
                        "El producto está inactivo: "
                                + product.getName()
                );
            }

            QuoteItem item = QuoteItem.create(
                    product.getId(),
                    product.getName(),
                    product.getUnit(),
                    itemRequest.quantity(),
                    product.getUnitPrice()
            );

            newItems.add(item);
        }

        quote.replaceItems(newItems);

        Quote savedQuote = quoteRepository.save(quote);

        return QuoteMapper.toResponse(savedQuote);
    }

    @Override
    public QuoteResponse cancel(UUID id) {

        Quote quote = quoteRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Cotización no encontrada con id: " + id
                        )
                );

        quote.cancel();

        Quote savedQuote = quoteRepository.save(quote);

        return QuoteMapper.toResponse(savedQuote);
    }
}