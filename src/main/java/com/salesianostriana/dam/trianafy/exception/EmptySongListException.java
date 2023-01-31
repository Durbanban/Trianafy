package com.salesianostriana.dam.trianafy.exception;

import javax.persistence.EntityNotFoundException;


public class EmptySongListException extends EntityNotFoundException {

    public EmptySongListException() {
        super("No se han encontrado canciones");
    }
}
