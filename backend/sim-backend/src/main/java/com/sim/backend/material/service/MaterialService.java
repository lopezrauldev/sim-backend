package com.sim.backend.material.service;

import com.sim.backend.material.dto.MaterialRequest;
import com.sim.backend.material.dto.MaterialResponse;
import com.sim.backend.material.entity.Material;
import com.sim.backend.material.mapper.MaterialMapper;
import com.sim.backend.material.repository.MaterialRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class MaterialService {

    private final MaterialRepository materialRepository;

    public MaterialService(MaterialRepository materialRepository) {
        this.materialRepository = materialRepository;
    }

    public MaterialResponse create(MaterialRequest request) {
        String normalizedCode = request.code().trim();

        if (materialRepository.existsByCode(normalizedCode)) {
            throw new IllegalArgumentException("Ya existe un material con el codigo: " + normalizedCode);
        }

        Material material = MaterialMapper.toDomain(request);
        Material savedMaterial = materialRepository.save(material);

        return MaterialMapper.toResponse(savedMaterial);
    }

    public List<MaterialResponse> findAll() {
        return materialRepository.findAll()
                .stream()
                .map(MaterialMapper::toResponse)
                .toList();
    }

    public MaterialResponse findById(UUID id) {
        Material material = materialRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("No existe un material con el id: " + id));

        return MaterialMapper.toResponse(material);
    }

    public MaterialResponse findByCode(String code) {
        String normalizedCode = code.trim();

        Material material = materialRepository.findByCode(normalizedCode)
                .orElseThrow(() -> new IllegalArgumentException("No existe un material con el codigo: " + normalizedCode));

        return MaterialMapper.toResponse(material);
    }

    public MaterialResponse update(UUID id, MaterialRequest request) {
        Material material = materialRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("No existe un material con el id: "+id));

        String normalizedCode = request.code().trim();

        materialRepository.findByCode(normalizedCode)
                .filter(existingMaterial ->
                        !existingMaterial.getId().equals(id)
                )
                .ifPresent(existingMaterial -> {
                    throw new IllegalArgumentException(
                            "ya existe otro material con el codigo: " + normalizedCode
                    );
                });
        MaterialMapper.updateDomain(material, request);

        Material updateMaterial = materialRepository.save(material);

        return MaterialMapper.toResponse(updateMaterial);
    }

    public void delete(UUID id) {
        Material material = materialRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(
                        "No existe un material con el id: " + id
                ));

        material.deactivate();

        materialRepository.save(material);
    }
}
