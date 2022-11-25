package com.salesianostriana.dam.trianafy.repos;

import com.salesianostriana.dam.trianafy.model.Playlist;
import com.salesianostriana.dam.trianafy.model.Song;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PlaylistRepository extends JpaRepository<Playlist, Long> {

    @Query(value = "SELECT pl FROM Playlist pl WHERE :song MEMBER OF pl.songs")
    public List<Playlist> findPlaylistBySong(@Param("song") Song song);
}
