package com.sim.backend.client.exception;

public class ClientAlreadyExistsException extends RuntimeException{

    public ClientAlreadyExistsException(String documentNumber) {
        super(
                "Ya existe un cliente con eldocumento:  " + documentNumber );
    }
}
