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
import com.sim.backend.quote.repository.QuoteRepository;
import com.sim.backend.shared.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class DefaultQuoteService implements QuoteService {

    private final QuoteRepository quoteRepository;
    private final ClientRepository clientRepository;
    private final ProductRepository productRepository;

    public DefaultQuoteService(
            QuoteRepository quoteRepository,
            ClientRepository clientRepository,
            ProductRepository productRepository
    ) {
        this.quoteRepository = quoteRepository;
        this.clientRepository = clientRepository;
        this.productRepository = productRepository;
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

        String number = generateQuoteNumber();

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

    private String generateQuoteNumber() {

        return "COT-" + UUID.randomUUID()
                .toString()
                .substring(0, 8)
                .toUpperCase();
    }
}