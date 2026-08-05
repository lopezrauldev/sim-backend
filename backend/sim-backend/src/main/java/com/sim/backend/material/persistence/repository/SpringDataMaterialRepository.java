package com.sim.backend.material.persistence.repository;

import com.sim.backend.material.persistence.entity.MaterialJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface SpringDataMaterialRepository extends JpaRepository<MaterialJpaEntity, UUID> {

    boolean existsByCode(String code);

    Optional<MaterialJpaEntity> findByCode(String code);
}
