package com.sim.backend.material.repository;

import com.sim.backend.material.entity.Material;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface MaterialRepository {

    Material save(Material material);

    Optional<Material> findById(UUID id);

    Optional<Material> findByCode(String code);

    List<Material> findAll();

    boolean existsByCode(String code);

    void deleteById(UUID id);
}
