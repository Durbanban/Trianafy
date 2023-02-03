package com.salesianostriana.dam.trianafy.validation.annotation;


import com.salesianostriana.dam.trianafy.validation.validator.UniqueSongValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = UniqueSongValidator.class)
@Documented
public @interface UniqueSong {

    String message() default "El nombre de la canción no debe repetirse. La canción ya existe para el artista";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    String titleField();

    String artistIdField();

    String albumField();


}
