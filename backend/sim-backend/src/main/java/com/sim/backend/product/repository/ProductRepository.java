package com.sim.backend.product.repository;

import com.sim.backend.product.entity.Product;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ProductRepository {

    Product save(Product product);

    Optional<Product> findById(UUID id);

    Optional<Product> findByCode(String code);

    List<Product> findAll();

    boolean existsByCode(String code);

    void deleteById(UUID id);
}
