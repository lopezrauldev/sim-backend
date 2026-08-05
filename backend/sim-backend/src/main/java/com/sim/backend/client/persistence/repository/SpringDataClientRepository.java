package com.sim.backend.client.persistence.repository;

import com.sim.backend.client.persistence.entity.ClientJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface SpringDataClientRepository extends JpaRepository<ClientJpaEntity, UUID> {

    boolean existsByDocumentNumber(String documentNumber);

    Optional<ClientJpaEntity> findByDocumentNumber(String documentNumber);

    List<ClientJpaEntity> findByActiveTrue();


}
