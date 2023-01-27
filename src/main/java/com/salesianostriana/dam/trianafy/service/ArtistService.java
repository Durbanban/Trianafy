package com.salesianostriana.dam.trianafy.service;


import com.salesianostriana.dam.trianafy.exception.ArtistNotFoundException;
import com.salesianostriana.dam.trianafy.exception.EmptyArtistListException;
import com.salesianostriana.dam.trianafy.model.Artist;
import com.salesianostriana.dam.trianafy.repos.ArtistRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ArtistService {

    private final ArtistRepository repository;

    private final SongService songService;


    public Artist add(Artist artist) {
        return repository.save(artist);
    }

    public Artist findById(Long id) {
        return repository.findById(id).orElseThrow(() -> new ArtistNotFoundException(id));
    }

    public List<Artist> findAll() {
        List<Artist> result = repository.findAll();
        if(result.isEmpty()) {
            throw new EmptyArtistListException();
        }
        return result;
    }

    public Artist edit(Long id, Artist edited) {
        return repository.findById(id).map(artist -> {
            artist.setName(edited.getName());
            return repository.save(artist);
        }).orElseThrow(() -> new ArtistNotFoundException(id));
    }

    public void delete(Artist artist) {
        repository.delete(artist);
    }

    public void deleteById(Long id) {
        if(repository.existsById(id)) {
            songService.findByArtist(findById(id))
                    .stream()
                            .forEach(song -> song.setArtist(null));
            repository.deleteById(id);

        }
    }

    public boolean existsById(Long id) {
        return repository.existsById(id);
    }

    public boolean artistExists(String name) {
        return repository.existsByName(name);
    }

}
