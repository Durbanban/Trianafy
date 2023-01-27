package com.salesianostriana.dam.trianafy.validation.annotation;

import com.salesianostriana.dam.trianafy.validation.validator.UniqueArtistNameValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target({ElementType.FIELD, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = UniqueArtistNameValidator.class)
@Documented
public @interface UniqueArtistName {

    String message() default "El nombre del artiste no debe repetirse. El artista ya existe";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
