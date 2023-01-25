package com.salesianostriana.dam.trianafy.exception;

import javax.persistence.EntityNotFoundException;

public class EmptyArtistListException extends EntityNotFoundException {

    public EmptyArtistListException() {
        super("No se han encontrado artistas");
    }
}
