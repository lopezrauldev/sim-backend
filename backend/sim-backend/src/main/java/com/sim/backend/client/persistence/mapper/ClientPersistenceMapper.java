package com.sim.backend.client.persistence.mapper;

import com.sim.backend.client.entity.Client;
import com.sim.backend.client.persistence.entity.ClientJpaEntity;
import org.springframework.stereotype.Component;

@Component
public class ClientPersistenceMapper {

    public ClientJpaEntity toJpaEntity(Client client) {
        if(client == null){
            throw new IllegalArgumentException(
                    "El cliente no puede ser nulo"
                    );
        }
        return new ClientJpaEntity(
                client.getId(),
                client.getType(),
                client.getDocumentNumber(),
                client.getName(),
                client.getBusinessName(),
                client.getAddress(),
                client.getDepartment(),
                client.getProvince(),
                client.getDistrict(),
                client.getEmail(),
                client.getPhone(),
                client.isActive()
        );
    }

    public Client toDomain(ClientJpaEntity entity){
        if(entity == null){
            throw new IllegalArgumentException(
                    "la entidad JPA del cliente no puede ser nula"
            );
        }

        return Client.restore(
                entity.getId(),
                entity.getType(),
                entity.getDocumentNumber(),
                entity.getName(),
                entity.getBusinessName(),
                entity.getAddress(),
                entity.getDepartment(),
                entity.getProvince(),
                entity.getDistrict(),
                entity.getEmail(),
                entity.getPhone(),
                entity.isActive()

        );
    }
}
