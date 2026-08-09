package com.sim.backend.product.service;

import com.sim.backend.product.dto.ProductRequest;
import com.sim.backend.product.dto.ProductResponse;

import java.util.List;
import java.util.UUID;

public interface ProductService {

    ProductResponse create(ProductRequest request);

    ProductResponse update(UUID id, ProductRequest request);

    ProductResponse findById(UUID id);

    List<ProductResponse> findAll();

    void deleteById(UUID id);

    ProductResponse activate(UUID id);
}
