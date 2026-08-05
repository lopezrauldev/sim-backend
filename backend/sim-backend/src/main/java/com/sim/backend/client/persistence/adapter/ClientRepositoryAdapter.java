package com.sim.backend.client.persistence.adapter;

import com.sim.backend.client.entity.Client;
import com.sim.backend.client.persistence.entity.ClientJpaEntity;
import com.sim.backend.client.persistence.mapper.ClientPersistenceMapper;
import com.sim.backend.client.persistence.repository.SpringDataClientRepository;
import com.sim.backend.client.repository.ClientRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class ClientRepositoryAdapter implements ClientRepository {

    private final SpringDataClientRepository springDataRepository;
    private final ClientPersistenceMapper persistenceMapper;

    public ClientRepositoryAdapter(
            SpringDataClientRepository springDataRepository,
            ClientPersistenceMapper persistenceMapper
    ){
        this.springDataRepository = springDataRepository;
        this.persistenceMapper = persistenceMapper;
    }

    @Override
    public Client save(Client client){
        ClientJpaEntity jpaEntity = persistenceMapper.toJpaEntity(client);

        ClientJpaEntity savedEntity = springDataRepository.save(jpaEntity);

        return persistenceMapper.toDomain(savedEntity);
    }

    @Override
    public Optional<Client> findById(UUID id){
        return springDataRepository.findById(id)
                .map(persistenceMapper::toDomain);
    }

    @Override
    public List<Client> findAll(){
        return springDataRepository.findByActiveTrue()
                .stream()
                .map(persistenceMapper::toDomain)
                .toList();
    }

    @Override
    public void deleteById(UUID id) {
        springDataRepository.deleteById(id);
    }

    @Override
    public boolean existsByDocumentNumber(String documentNumber) {
        return springDataRepository.existsByDocumentNumber(documentNumber);
    }


}



















