package com.sim.backend.material.persistence.mapper;

import com.sim.backend.material.entity.Material;
import com.sim.backend.material.persistence.entity.MaterialJpaEntity;
import org.springframework.stereotype.Component;

@Component
public class MaterialPersistenceMapper {

    public MaterialJpaEntity toJpaEntity(Material material) {
        return new MaterialJpaEntity(
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

    public Material toDomain(MaterialJpaEntity entity) {
        return new Material(
                entity.getId(),
                entity.getCode(),
                entity.getName(),
                entity.getDescription(),
                entity.getSupplier(),
                entity.getCategory(),
                entity.getWeight(),
                entity.getDimensions(),
                entity.getUnit(),
                entity.getUnitPrice(),
                entity.getStock(),
                entity.isActive()
        );
    }
}
