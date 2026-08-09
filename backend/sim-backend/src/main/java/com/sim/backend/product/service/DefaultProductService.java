package com.sim.backend.product.service;

import com.sim.backend.product.dto.ProductRequest;
import com.sim.backend.product.dto.ProductResponse;
import com.sim.backend.product.entity.Product;
import com.sim.backend.product.mapper.ProductMapper;
import com.sim.backend.product.repository.ProductRepository;
import com.sim.backend.shared.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class DefaultProductService implements ProductService {

    private final ProductRepository productRepository;

    public DefaultProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public ProductResponse create(ProductRequest request) {

        if (productRepository.existsByCode(request.code())) {
            throw new IllegalArgumentException(
                    "Ya existe un producto con el código: " + request.code()
            );
        }

        Product product = ProductMapper.toDomain(request);

        Product savedProduct = productRepository.save(product);

        return ProductMapper.toResponse(savedProduct);
    }

    @Override
    public ProductResponse update(UUID id, ProductRequest request) {

        Product product = productRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Producto no encontrado con id: " + id
                        )
                );

        productRepository.findByCode(request.code())
                .filter(existing -> !existing.getId().equals(id))
                .ifPresent(existing -> {
                    throw new IllegalArgumentException(
                            "Ya existe un producto con el código: " + request.code()
                    );
                });

        product.update(
                request.code(),
                request.name(),
                request.description(),
                request.unit(),
                request.unitPrice()
        );

        Product updatedProduct = productRepository.save(product);

        return ProductMapper.toResponse(updatedProduct);
    }

    @Override
    public ProductResponse findById(UUID id) {

        Product product = productRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Producto no encontrado con id: " + id
                        )
                );

        return ProductMapper.toResponse(product);
    }

    @Override
    public List<ProductResponse> findAll() {

        return productRepository.findAll()
                .stream()
                .map(ProductMapper::toResponse)
                .toList();
    }

    @Override
    public void deleteById(UUID id) {

        Product product = productRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Producto no encontrado con id: " + id
                        )
                );

        product.deactivate();

        productRepository.save(product);
    }

    @Override
    public ProductResponse activate(UUID id) {

        Product product = productRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Producto no encontrado con id: " + id
                        )
                );

        product.activate();

        Product activatedProduct = productRepository.save(product);

        return ProductMapper.toResponse(activatedProduct);
    }
}
