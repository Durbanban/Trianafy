package com.salesianostriana.dam.trianafy.exception;

import javax.persistence.EntityNotFoundException;


public class PlaylistNotFoundException extends EntityNotFoundException {

    public PlaylistNotFoundException() {
        super("La lista de reproducción no ha sido encontrada");
    }

    public PlaylistNotFoundException(Long id) {
        super(String.format("La lista de reproducción con el id %d no ha sido encontrada", id));
    }
}
