package com.salesianostriana.dam.trianafy.validation.validator;

import com.salesianostriana.dam.trianafy.repos.ArtistRepository;
import com.salesianostriana.dam.trianafy.service.ArtistService;
import com.salesianostriana.dam.trianafy.validation.annotation.UniqueArtistName;
import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;



@NoArgsConstructor
public class UniqueArtistNameValidator implements ConstraintValidator<UniqueArtistName, String> {

    @Autowired
    private ArtistService artistService;




    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {

        boolean exists = artistService.artistExists(s);
        return StringUtils.hasText(s) && !exists;
    }
}
