package com.sim.backend.material.persistence.adapter;

import com.sim.backend.material.entity.Material;
import com.sim.backend.material.persistence.entity.MaterialJpaEntity;
import com.sim.backend.material.persistence.mapper.MaterialPersistenceMapper;
import com.sim.backend.material.repository.MaterialRepository;
import com.sim.backend.material.persistence.repository.SpringDataMaterialRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class MaterialRepositoryAdapter implements MaterialRepository {

    private final SpringDataMaterialRepository springDataRepository;
    private final MaterialPersistenceMapper persistenceMapper;

    public MaterialRepositoryAdapter(
            SpringDataMaterialRepository springDataRepository,
            MaterialPersistenceMapper persistenceMapper) {
        this.springDataRepository = springDataRepository;
        this.persistenceMapper = persistenceMapper;
    }

    @Override
    public Material save(Material material) {
        MaterialJpaEntity jpaEntity = persistenceMapper.toJpaEntity(material);
        MaterialJpaEntity savedEntity = springDataRepository.save(jpaEntity);

        return persistenceMapper.toDomain(savedEntity);
    }

    @Override
    public Optional<Material> findById(UUID id) {

        return springDataRepository.findById(id)
                .map(persistenceMapper::toDomain);
    }

    @Override
    public Optional<Material> findByCode(String code) {
        return springDataRepository.findByCode(code)
                .map(persistenceMapper::toDomain);
    }

    @Override
    public List<Material> findAll() {

        return springDataRepository.findAll()
                .stream()
                .map(persistenceMapper::toDomain)
                .toList();
    }

    @Override
    public boolean existsByCode(String code) {

        return springDataRepository.existsByCode(code);
    }

    @Override
    public void deleteById(UUID id) {

        springDataRepository.deleteById(id);
    }


}
































