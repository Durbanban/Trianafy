package com.salesianostriana.dam.trianafy.exception;

import javax.persistence.EntityNotFoundException;

public class SongNotFoundException extends EntityNotFoundException {

    public SongNotFoundException() {
        super("La canción no ha sido encontrada");
    }

    public SongNotFoundException(Long id) {
        super(String.format("La canción con el id %d no ha sido encontrada", id));
    }
}
