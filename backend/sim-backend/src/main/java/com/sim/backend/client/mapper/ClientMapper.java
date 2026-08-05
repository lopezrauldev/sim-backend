package com.sim.backend.client.mapper;

import com.sim.backend.client.dto.ClientRequest;
import com.sim.backend.client.dto.ClientResponse;
import com.sim.backend.client.entity.Client;
import org.springframework.stereotype.Component;

@Component
public class ClientMapper {

    public Client toEntity(ClientRequest request) {
        if (request == null) {
            throw new IllegalArgumentException(
                    "La solicitud del cliente no puede ser nula"
            );
        }

        return Client.create(
                request.getType(),
                request.getDocumentNumber(),
                request.getName(),
                request.getBusinessName(),
                request.getAddress(),
                request.getDepartment(),
                request.getProvince(),
                request.getDistrict(),
                request.getEmail(),
                request.getPhone()
        );
    }

    public ClientResponse toResponse(Client client) {
        if (client == null) {
            throw new IllegalArgumentException(
                    "El cliente no puede ser nulo"
            );
        }

        ClientResponse response = new ClientResponse();

        response.setId(client.getId());
        response.setType(client.getType());
        response.setDocumentNumber(client.getDocumentNumber());
        response.setName(client.getName());
        response.setBusinessName(client.getBusinessName());
        response.setAddress(client.getAddress());
        response.setDepartment(client.getDepartment());
        response.setProvince(client.getProvince());
        response.setDistrict(client.getDistrict());
        response.setEmail(client.getEmail());
        response.setPhone(client.getPhone());
        response.setActive(client.isActive());

        return response;
    }
}