package com.salesianostriana.dam.trianafy.exception;

import javax.persistence.EntityNotFoundException;


public class EmptyPlaylistException extends EntityNotFoundException {

    public EmptyPlaylistException() {
        super("No se han encontrado listas de reproducción");
    }
}
