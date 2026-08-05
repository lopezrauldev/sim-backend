package com.sim.backend.client.exception;

import java.util.UUID;

public class ClientNotFoundException extends RuntimeException{

    public ClientNotFoundException(UUID id){
        super("No se encontroel cliente con id: " + id);
    }
}
