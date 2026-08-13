package com.sim.backend.client.service.impl;

import com.sim.backend.client.dto.ClientRequest;
import com.sim.backend.client.dto.ClientResponse;
import com.sim.backend.client.entity.Client;
import com.sim.backend.client.exception.ClientNotFoundException;
import com.sim.backend.client.exception.ClientAlreadyExistsException;
import com.sim.backend.client.mapper.ClientMapper;
import com.sim.backend.client.repository.ClientRepository;
import com.sim.backend.client.service.ClientService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class ClientServiceImpl implements ClientService {

    private final ClientRepository clientRepository;
    private final ClientMapper clientMapper;

    public ClientServiceImpl(
            ClientRepository clientRepository,
            ClientMapper clientMapper
    ) {
        this.clientRepository = clientRepository;
        this.clientMapper = clientMapper;
    }

    @Override
    public ClientResponse create(ClientRequest request) {
        String documentNumber = normalizeDocument(
                request.getDocumentNumber()
        );

        if (clientRepository.existsByDocumentNumber(documentNumber)) {
            throw new ClientAlreadyExistsException(documentNumber);
        }

        Client client = clientMapper.toEntity(request);
        Client savedClient = clientRepository.save(client);

        return clientMapper.toResponse(savedClient);
    }

    @Override
    public ClientResponse update(UUID id, ClientRequest request) {
        Client client = findClientById(id);

        String documentNumber = normalizeDocument(
                request.getDocumentNumber());

        boolean documentChanged =
                !client.getDocumentNumber().equals(documentNumber);

        if (documentChanged
                && clientRepository.existsByDocumentNumber(documentNumber)) {
            throw new ClientAlreadyExistsException(documentNumber);
        }

        client.updateIdentityData(
                request.getType(),
                documentNumber,
                request.getName(),
                request.getBusinessName()
        );

        client.updateContactInfo(
                request.getAddress(),
                request.getDepartment(),
                request.getProvince(),
                request.getDistrict(),
                request.getEmail(),
                request.getPhone()
        );

        Client updatedClient = clientRepository.save(client);

        return clientMapper.toResponse(updatedClient);
    }

    @Override
    @Transactional(readOnly = true)
    public ClientResponse findById(UUID id){
        Client client = findClientById(id);
        return clientMapper.toResponse(client);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ClientResponse> findAll() {
        return clientRepository.findAll()
                .stream()
                .map(clientMapper::toResponse)
                .toList();
    }

    @Override
    public void delete(UUID id){
        Client client = findClientById(id);
        client.deactivate();
        clientRepository.save(client);
    }

    private Client findClientById(UUID id) {
        return clientRepository.findById(id)
                .orElseThrow(() -> new ClientNotFoundException(id));
    }

    private String normalizeDocument (String documentNumber){

        return documentNumber == null
                ? ""
                : documentNumber.trim();
    }
}
















