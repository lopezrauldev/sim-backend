package com.sim.backend.client.service;

import com.sim.backend.client.dto.ClientRequest;
import com.sim.backend.client.dto.ClientResponse;

import java.util.List;
import java.util.UUID;

public interface ClientService {

    ClientResponse create(ClientRequest request);

    ClientResponse update(UUID id, ClientRequest request);

    ClientResponse findById(UUID id);

    List<ClientResponse> findAll();

    void delete(UUID id);
}
