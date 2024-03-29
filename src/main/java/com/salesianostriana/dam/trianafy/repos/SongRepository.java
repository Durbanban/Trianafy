package com.salesianostriana.dam.trianafy.repos;

import com.salesianostriana.dam.trianafy.model.Artist;
import com.salesianostriana.dam.trianafy.model.Song;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SongRepository extends JpaRepository<Song, Long> {

    public List<Song> findByArtist(Artist artist);

    public List<Song> findByTitleIgnoreCase(String title);


}
