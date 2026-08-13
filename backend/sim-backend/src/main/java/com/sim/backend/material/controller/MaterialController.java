package com.sim.backend.material.controller;

import com.sim.backend.material.dto.MaterialRequest;
import com.sim.backend.material.dto.MaterialResponse;
import com.sim.backend.material.service.MaterialService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/materials")
public class MaterialController {

    private final MaterialService materialService;

    public MaterialController(MaterialService materialService) {
        this.materialService = materialService;
    }

    @PostMapping
    public ResponseEntity<MaterialResponse> create(@Valid @RequestBody MaterialRequest request) {
        MaterialResponse response = materialService.create(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @GetMapping
    public ResponseEntity<List<MaterialResponse>> findAll() {
        return ResponseEntity.ok(materialService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<MaterialResponse> findById(@PathVariable UUID id) {
        return ResponseEntity.ok(materialService.findById(id));
    }

    @GetMapping("/code/{code}")
    public ResponseEntity<MaterialResponse> findByCode(@PathVariable String code) {
        return ResponseEntity.ok(materialService.findByCode(code));
    }

    @PutMapping("/{id}")
    public ResponseEntity<MaterialResponse> update(
            @PathVariable UUID id, @Valid @RequestBody MaterialRequest request) {

        return ResponseEntity.ok(materialService.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        materialService.delete(id);

        return ResponseEntity.noContent().build();
    }

}


















