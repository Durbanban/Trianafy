package com.salesianostriana.dam.trianafy.validation.validator;

import com.salesianostriana.dam.trianafy.model.Artist;
import com.salesianostriana.dam.trianafy.model.Song;
import com.salesianostriana.dam.trianafy.service.ArtistService;
import com.salesianostriana.dam.trianafy.service.SongService;
import com.salesianostriana.dam.trianafy.validation.annotation.UniqueSong;
import lombok.NoArgsConstructor;
import org.springframework.beans.PropertyAccessorFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.List;

@NoArgsConstructor
public class UniqueSongValidator implements ConstraintValidator<UniqueSong, Object> {

    @Autowired
    private ArtistService artistService;

    @Autowired
    private SongService songService;

    String titleField;

    String artistIdField;

    String albumField;


    @Override
    public void initialize(UniqueSong constraintAnnotation) {
        this.titleField = constraintAnnotation.titleField();
        this.artistIdField = constraintAnnotation.artistIdField();
        this.albumField = constraintAnnotation.albumField();
    }

    @Override
    public boolean isValid(Object obj, ConstraintValidatorContext context) {

        String title = (String) PropertyAccessorFactory
                .forBeanPropertyAccess(obj)
                .getPropertyValue(this.titleField);

        Long artistId = (Long) PropertyAccessorFactory
                .forBeanPropertyAccess(obj)
                .getPropertyValue(this.artistIdField);

        String album = (String) PropertyAccessorFactory
                .forBeanPropertyAccess(obj)
                .getPropertyValue(this.albumField);

        boolean resultado;

        List<Song> lista = songService.findByTitle(title);
        Artist artista = artistService.findById(artistId);


        resultado = lista
                .stream()
                .anyMatch(song -> song.getTitle().equalsIgnoreCase(title) && song.getArtist().equals(artista) && song.getAlbum().equalsIgnoreCase(album));

        return StringUtils.hasText(title) && !resultado;



    }
}
