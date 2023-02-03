package com.salesianostriana.dam.trianafy.exception;


public class ArtistHasSongsException extends RuntimeException{

    public ArtistHasSongsException() {
        super("No se puede borrar al artista debido a que aún tiene canciones asociadas");
    }

    public ArtistHasSongsException(Long id) {
        super(String.format("No se puede borrar al artista con el id %d debido a que aún tiene canciones asociadas", id));
    }

}
