package com.sim.backend.product.persistence.adapter;

import com.sim.backend.product.entity.ProductMaterial;
import com.sim.backend.product.persistence.entity.ProductMaterialJpaEntity;
import com.sim.backend.product.persistence.mapper.ProductMaterialPersistenceMapper;
import com.sim.backend.product.persistence.repository.SpringDataProductMaterialRepository;
import com.sim.backend.product.repository.ProductMaterialRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class ProductMaterialRepositoryAdapter
        implements ProductMaterialRepository {

    private final SpringDataProductMaterialRepository springDataRepository;

    public ProductMaterialRepositoryAdapter(
            SpringDataProductMaterialRepository springDataRepository
    ) {
        this.springDataRepository = springDataRepository;
    }

    @Override
    public ProductMaterial save(ProductMaterial productMaterial) {

        ProductMaterialJpaEntity entity =
                ProductMaterialPersistenceMapper.toJpaEntity(productMaterial);

        ProductMaterialJpaEntity savedEntity =
                springDataRepository.save(entity);

        return ProductMaterialPersistenceMapper.toDomain(savedEntity);
    }

    @Override
    public Optional<ProductMaterial> findById(UUID id) {

        return springDataRepository.findById(id)
                .map(ProductMaterialPersistenceMapper::toDomain);
    }

    @Override
    public List<ProductMaterial> findByProductId(UUID productId) {

        return springDataRepository.findByProductId(productId)
                .stream()
                .map(ProductMaterialPersistenceMapper::toDomain)
                .toList();
    }

    @Override
    public boolean existsByProductIdAndMaterialId(
            UUID productId,
            UUID materialId
    ) {
        return springDataRepository.existsByProductIdAndMaterialId(
                productId,
                materialId
        );
    }

    @Override
    public void deleteById(UUID id) {
        springDataRepository.deleteById(id);
    }
}
