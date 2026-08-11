package com.sim.backend.quote.service;

import com.sim.backend.client.repository.ClientRepository;
import com.sim.backend.product.dto.ProductMaterialResponse;
import com.sim.backend.product.entity.Product;
import com.sim.backend.product.repository.ProductRepository;
import com.sim.backend.product.service.ProductMaterialService;
import com.sim.backend.quote.dto.*;
import com.sim.backend.quote.entity.Quote;
import com.sim.backend.quote.entity.QuoteItem;
import com.sim.backend.quote.mapper.QuoteMapper;
import com.sim.backend.quote.repository.QuoteNumberGenerator;
import com.sim.backend.quote.repository.QuoteRepository;
import com.sim.backend.shared.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.UUID;
import java.util.Map;

@Service
public class DefaultQuoteService implements QuoteService {

    private final QuoteRepository quoteRepository;
    private final ClientRepository clientRepository;
    private final ProductRepository productRepository;
    private final QuoteNumberGenerator quoteNumberGenerator;
    private final ProductMaterialService productMaterialService;

    public DefaultQuoteService(
            QuoteRepository quoteRepository,
            ClientRepository clientRepository,
            ProductRepository productRepository,
            QuoteNumberGenerator quoteNumberGenerator,
            ProductMaterialService productMaterialService
    ) {
        this.quoteRepository = quoteRepository;
        this.clientRepository = clientRepository;
        this.productRepository = productRepository;
        this.quoteNumberGenerator = quoteNumberGenerator;
        this.productMaterialService = productMaterialService;
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

    @Override
    public List<QuoteMaterialResponse> getRequiredMaterials(UUID quoteId) {

        Quote quote = quoteRepository.findById(quoteId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Cotización no encontrada con id: " + quoteId
                        )
                );

        Map<UUID, QuoteMaterialResponse> consolidatedMaterials =
                new LinkedHashMap<>();

        for (QuoteItem item : quote.getItems()) {

            List<ProductMaterialResponse> productMaterials =
                    productMaterialService.findByProductId(
                            item.getProductId()
                    );

            for (ProductMaterialResponse productMaterial : productMaterials) {

                BigDecimal requiredQuantity =
                        productMaterial.baseQuantity()
                                .multiply(item.getQuantity());

                consolidatedMaterials.merge(
                        productMaterial.materialId(),

                        new QuoteMaterialResponse(
                                productMaterial.materialId(),
                                productMaterial.materialCode(),
                                productMaterial.materialName(),
                                productMaterial.materialUnit(),
                                requiredQuantity
                        ),

                        (existing, incoming) ->
                                new QuoteMaterialResponse(
                                        existing.materialId(),
                                        existing.materialCode(),
                                        existing.materialName(),
                                        existing.materialUnit(),
                                        existing.requiredQuantity()
                                                .add(incoming.requiredQuantity())
                                )
                );
            }
        }

        return new ArrayList<>(consolidatedMaterials.values());
    }
}