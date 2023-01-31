package com.salesianostriana.dam.trianafy.service;


import com.salesianostriana.dam.trianafy.exception.EmptySongListException;
import com.salesianostriana.dam.trianafy.exception.SongNotFoundException;
import com.salesianostriana.dam.trianafy.model.Artist;
import com.salesianostriana.dam.trianafy.model.Song;
import com.salesianostriana.dam.trianafy.repos.SongRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SongService {

    private final SongRepository repository;

    private final PlaylistService playlistService;

    public Song add(Song song) {
        return repository.save(song);
    }

    public Song findById(Long id) {
        return repository.findById(id).orElseThrow(() -> new SongNotFoundException(id));
    }

    public List<Song> findAll() {
        List<Song> songs = repository.findAll();
        if(songs.isEmpty()) {
            throw new EmptySongListException();
        }
        return songs;
    }

    public Song edit(Long id, Song toEdit) {
        return repository.findById(id).map(song -> {
            song.setTitle(toEdit.getTitle());
            song.setYear(toEdit.getYear());
            song.setAlbum(toEdit.getAlbum());
            song.setArtist(toEdit.getArtist());
            return repository.save(song);
        }).orElseThrow(() -> new SongNotFoundException(id));
    }

    public void delete(Song song) {
        repository.delete(song);
    }

    public void deleteById(Long id) {
        if(repository.existsById(id)) {
            Song toDelete = findById(id);
            playlistService.findPlaylistBySong(toDelete).stream().forEach(pl -> {
                while(pl.getSongs().contains(toDelete)) {
                    pl.deleteSong(toDelete);
                }
            });
            repository.deleteById(id);
        }
    }

    public List<Song> findByArtist(Artist artist) {
        return repository.findByArtist(artist);

    }

    public List<Song> findByTitle(String title) {
        return repository.findByTitleIgnoreCase(title);
    }

    public boolean existsById(Long id) {
        return repository.existsById(id);
    }

}
