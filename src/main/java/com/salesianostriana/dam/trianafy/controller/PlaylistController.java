package com.salesianostriana.dam.trianafy.controller;

import com.salesianostriana.dam.trianafy.dto.PlaylistDtoConverter;
import com.salesianostriana.dam.trianafy.dto.PlaylistDtoRequest;
import com.salesianostriana.dam.trianafy.dto.PlaylistDtoResponseAll;
import com.salesianostriana.dam.trianafy.dto.PlaylistDtoResponseCreation;
import com.salesianostriana.dam.trianafy.model.Playlist;
import com.salesianostriana.dam.trianafy.service.PlaylistService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class PlaylistController {

    private final PlaylistService playlistService;

    private final PlaylistDtoConverter playlistDtoConverter;

    @GetMapping("/list/")
    public ResponseEntity<List<PlaylistDtoResponseAll>> getAllPlaylists() {
        if(playlistService.findAll().isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }else {
            List<PlaylistDtoResponseAll> listDto = playlistService.findAll()
                                                                    .stream()
                                                                    .map(playlistDtoConverter::toPlaylistDto)
                                                                    .collect(Collectors.toList());
            return ResponseEntity.status(HttpStatus.OK).body(listDto);

        }
    }

    @GetMapping("/list/{id}")
    public ResponseEntity<Playlist> getPlaylistById(@PathVariable Long id) {
        if(playlistService.existsById(id)) {
            return ResponseEntity.of(playlistService.findById(id));
        }else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PostMapping("/list/")
    public ResponseEntity<PlaylistDtoResponseCreation> createPlaylist(@RequestBody PlaylistDtoRequest playlistDtoRequest) {
        Playlist playlist = playlistDtoConverter.toPlaylist(playlistDtoRequest);
        if(playlistDtoRequest.getName() == null || playlistDtoRequest.getDescription() == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }else {
            return ResponseEntity.status(HttpStatus.CREATED).body(playlistDtoConverter.toPlayListDtoCreation(playlistService.add(playlist)));
        }

    }

}
