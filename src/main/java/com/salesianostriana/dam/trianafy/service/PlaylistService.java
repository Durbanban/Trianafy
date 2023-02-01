package com.salesianostriana.dam.trianafy.service;


import com.salesianostriana.dam.trianafy.exception.EmptyPlaylistException;
import com.salesianostriana.dam.trianafy.exception.PlaylistNotFoundException;
import com.salesianostriana.dam.trianafy.exception.SongNotFoundException;
import com.salesianostriana.dam.trianafy.model.Playlist;
import com.salesianostriana.dam.trianafy.model.Song;
import com.salesianostriana.dam.trianafy.repos.PlaylistRepository;
import com.salesianostriana.dam.trianafy.repos.SongRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PlaylistService {

    private final PlaylistRepository repository;

    public Playlist add(Playlist playlist) {
        return repository.save(playlist);
    }

    public Playlist findById(Long id) {
        return repository.findById(id).orElseThrow(() -> new PlaylistNotFoundException(id));
    }

    public List<Playlist> findAll() {
        List<Playlist> playlists = repository.findAll();
        if(playlists.isEmpty()) {
            throw new EmptyPlaylistException();
        }
        return playlists;
    }

    public Playlist edit(Long id, Playlist toEdit) {

        return repository.findById(id).map(pl -> {
            pl.setName(toEdit.getName());
            pl.setDescription(toEdit.getDescription());
            return repository.save(pl);
        }).orElseThrow(() -> new PlaylistNotFoundException(id));
    }

    public void delete(Playlist playlist) {
        repository.delete(playlist);
    }

    public void deleteById(Long id) {
        if(existsById(id)) {
            repository.deleteById(id);

        }
    }

    public boolean existsById(Long id) {
        return repository.existsById(id);
    }

    public List<Playlist> findPlaylistBySong(Song song) {
        return repository.findPlaylistBySong(song);
    }

    public Song findSongFromPlaylist(Long idPl, Long idSong) {

        Playlist playlist = findById(idPl);

        boolean result;

        result = playlist.getSongs()
                                .stream()
                                .anyMatch(s -> s.getId() == idSong);


        if(result) {
            return playlist.getSongs()
                                .stream()
                                .filter(s -> s.getId() == idSong)
                                .findFirst().get();
        }else {
            throw new SongNotFoundException();
        }

    }

    public void deleteSongFromPlaylist(Long idPl, Long idSong) {
        Playlist playlist = findById(idPl);

        boolean result;

        result = playlist.getSongs()
                                .stream()
                                .anyMatch(s -> s.getId() == idSong);

        if(!result) {
            throw new SongNotFoundException(idSong);
        }

        while(result) {
            Song song = playlist.getSongs()
                                    .stream()
                                    .filter(s -> s.getId() == idSong)
                                    .findFirst().get();
            playlist.deleteSong(song);
            result = playlist.getSongs()
                    .stream()
                    .anyMatch(s -> s.getId() == idSong);
        }

        add(playlist);


    }



}
