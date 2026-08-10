package com.sim.backend.product.service;

import com.sim.backend.material.entity.Material;
import com.sim.backend.material.repository.MaterialRepository;
import com.sim.backend.product.dto.ProductMaterialQuantityRequest;
import com.sim.backend.product.dto.ProductMaterialRequest;
import com.sim.backend.product.dto.ProductMaterialResponse;
import com.sim.backend.product.entity.ProductMaterial;
import com.sim.backend.product.mapper.ProductMaterialMapper;
import com.sim.backend.product.repository.ProductMaterialRepository;
import com.sim.backend.product.repository.ProductRepository;
import com.sim.backend.shared.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class DefaultProductMaterialService implements ProductMaterialService {

    private final ProductMaterialRepository productMaterialRepository;
    private final ProductRepository productRepository;
    private final MaterialRepository materialRepository;

    public DefaultProductMaterialService(
            ProductMaterialRepository productMaterialRepository,
            ProductRepository productRepository,
            MaterialRepository materialRepository
    ) {
        this.productMaterialRepository = productMaterialRepository;
        this.productRepository = productRepository;
        this.materialRepository = materialRepository;
    }

    @Override
    public ProductMaterialResponse addMaterial(
            UUID productId,
            ProductMaterialRequest request
    ) {

        productRepository.findById(productId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Producto no encontrado con id: " + productId
                        )
                );

        Material material = materialRepository.findById(request.materialId())
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Material no encontrado con id: "
                                        + request.materialId()
                        )
                );

        if (productMaterialRepository.existsByProductIdAndMaterialId(
                productId,
                request.materialId()
        )) {
            throw new IllegalArgumentException(
                    "El material ya está asociado al producto"
            );
        }

        ProductMaterial productMaterial =
                ProductMaterialMapper.toDomain(productId, request);

        ProductMaterial savedProductMaterial =
                productMaterialRepository.save(productMaterial);

        return ProductMaterialMapper.toResponse(
                savedProductMaterial,
                material
        );
    }

    @Override
    public List<ProductMaterialResponse> findByProductId(UUID productId) {

        productRepository.findById(productId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Producto no encontrado con id: " + productId
                        )
                );

        return productMaterialRepository.findByProductId(productId)
                .stream()
                .map(productMaterial -> {

                    Material material = materialRepository
                            .findById(productMaterial.getMaterialId())
                            .orElseThrow(() ->
                                    new ResourceNotFoundException(
                                            "Material no encontrado con id: "
                                                    + productMaterial.getMaterialId()
                                    )
                            );

                    return ProductMaterialMapper.toResponse(
                            productMaterial,
                            material
                    );
                })
                .toList();
    }

    @Override
    public ProductMaterialResponse updateBaseQuantity(
            UUID productId,
            UUID productMaterialId,
            ProductMaterialQuantityRequest request
    ) {

        ProductMaterial productMaterial =
                productMaterialRepository.findById(productMaterialId)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Relación producto-material no encontrada con id: "
                                                + productMaterialId
                                )
                        );

        if (!productMaterial.getProductId().equals(productId)) {
            throw new IllegalArgumentException(
                    "El material no pertenece al producto indicado"
            );
        }

        Material material = materialRepository
                .findById(productMaterial.getMaterialId())
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Material no encontrado con id: "
                                        + productMaterial.getMaterialId()
                        )
                );

        productMaterial.updateBaseQuantity(request.baseQuantity());

        ProductMaterial updatedProductMaterial =
                productMaterialRepository.save(productMaterial);

        return ProductMaterialMapper.toResponse(
                updatedProductMaterial,
                material
        );
    }

    @Override
    public void removeMaterial(
            UUID productId,
            UUID productMaterialId
    ) {

        ProductMaterial productMaterial =
                productMaterialRepository.findById(productMaterialId)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Relación producto-material no encontrada con id: "
                                                + productMaterialId
                                )
                        );

        if (!productMaterial.getProductId().equals(productId)) {
            throw new IllegalArgumentException(
                    "El material no pertenece al producto indicado"
            );
        }

        productMaterialRepository.deleteById(productMaterialId);
    }
}
