package com.sim.backend.material.mapper;

import com.sim.backend.material.dto.MaterialRequest;
import com.sim.backend.material.dto.MaterialResponse;
import com.sim.backend.material.entity.Material;

import java.util.UUID;

public class MaterialMapper {

    private MaterialMapper() {

    }

    public static Material toDomain(MaterialRequest request) {

        return new Material(
                UUID.randomUUID(),
                request.code(),
                request.name(),
                request.description(),
                request.supplier(),
                request.category(),
                request.weight(),
                request.dimensions(),
                request.unit(),
                request.unitPrice(),
                request.stock(),
                request.active() == null || request.active()

        );
    }

    public static MaterialResponse toResponse(Material material) {
        return new MaterialResponse(
                material.getId(),
                material.getCode(),
                material.getName(),
                material.getDescription(),
                material.getSupplier(),
                material.getCategory(),
                material.getWeight(),
                material.getDimensions(),
                material.getUnit(),
                material.getUnitPrice(),
                material.getStock(),
                material.isActive()
        );
    }

    public static void updateDomain(Material material, MaterialRequest request) {
        material.setCode(request.code());
        material.setName(request.name());
        material.setDescription(request.description());
        material.setSupplier(request.supplier());
        material.setCategory(request.category());
        material.setWeight(request.weight());
        material.setDimensions(request.dimensions());
        material.setUnit(request.unit());
        material.setUnitPrice(request.unitPrice());
        material.setStock(request.stock());

        if (request.active() != null) {
            if (request.active()){
                material.activate();
            } else{
                material.deactivate();
            }
        }
    }
}
