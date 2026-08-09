package com.sim.backend.product.persistence.adapter;

import com.sim.backend.product.entity.Product;
import com.sim.backend.product.repository.ProductRepository;
import com.sim.backend.product.persistence.mapper.ProductPersistenceMapper;
import com.sim.backend.product.persistence.repository.SpringDataProductRepository;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class ProductRepositoryAdapter implements ProductRepository {

    private final SpringDataProductRepository repository;

    public ProductRepositoryAdapter(SpringDataProductRepository repository) {
        this.repository = repository;
    }

    @Override
    public Product save(Product product) {
        var entity = ProductPersistenceMapper.toJpaEntity(product);
        var savedEntity = repository.save(entity);

        return ProductPersistenceMapper.toDomain(savedEntity);
    }

    @Override
    public Optional<Product> findById(UUID id) {
        return repository.findById(id)
                .map(ProductPersistenceMapper::toDomain);
    }

    @Override
    public List<Product> findAll() {
        return repository.findAll()
                .stream()
                .map(ProductPersistenceMapper::toDomain)
                .toList();
    }

    @Override
    public Optional<Product> findByCode(String code) {
        return repository.findByCode(code)
                .map(ProductPersistenceMapper::toDomain);
    }

    @Override
    public boolean existsByCode(String code) {
        return repository.existsByCode(code);
    }

    @Override
    public void deleteById(UUID id) {
        repository.deleteById(id);
    }
}

