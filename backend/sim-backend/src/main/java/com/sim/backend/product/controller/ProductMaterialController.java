package com.sim.backend.product.controller;

import com.sim.backend.product.dto.ProductMaterialQuantityRequest;
import com.sim.backend.product.dto.ProductMaterialRequest;
import com.sim.backend.product.dto.ProductMaterialResponse;
import com.sim.backend.product.service.ProductMaterialService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/products/{productId}/materials")
public class ProductMaterialController {

    private final ProductMaterialService productMaterialService;

    public ProductMaterialController(
            ProductMaterialService productMaterialService
    ) {
        this.productMaterialService = productMaterialService;
    }

    @PostMapping
    public ResponseEntity<ProductMaterialResponse> addMaterial(
            @PathVariable UUID productId,
            @Valid @RequestBody ProductMaterialRequest request
    ) {

        ProductMaterialResponse response =
                productMaterialService.addMaterial(productId, request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @GetMapping
    public ResponseEntity<List<ProductMaterialResponse>> findByProductId(
            @PathVariable UUID productId
    ) {

        return ResponseEntity.ok(
                productMaterialService.findByProductId(productId)
        );
    }

    @PatchMapping("/{productMaterialId}")
    public ResponseEntity<ProductMaterialResponse> updateBaseQuantity(
            @PathVariable UUID productId,
            @PathVariable UUID productMaterialId,
            @Valid @RequestBody ProductMaterialQuantityRequest request
    ) {

        return ResponseEntity.ok(
                productMaterialService.updateBaseQuantity(
                        productId,
                        productMaterialId,
                        request
                )
        );
    }

    @DeleteMapping("/{productMaterialId}")
    public ResponseEntity<Void> removeMaterial(
            @PathVariable UUID productId,
            @PathVariable UUID productMaterialId
    ) {

        productMaterialService.removeMaterial(
                productId,
                productMaterialId
        );

        return ResponseEntity.noContent().build();
    }
}