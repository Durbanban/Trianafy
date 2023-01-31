package com.salesianostriana.dam.trianafy.validation.validator;

import com.salesianostriana.dam.trianafy.validation.annotation.UniqueSong;
import org.springframework.beans.PropertyAccessorFactory;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class UniqueSongValidator implements ConstraintValidator<UniqueSong, Object> {

    String titleField;

    String artistIdField;


    @Override
    public void initialize(UniqueSong constraintAnnotation) {
        this.titleField = constraintAnnotation.titleField();
        this.artistIdField = constraintAnnotation.artistIdField();
    }

    @Override
    public boolean isValid(Object obj, ConstraintValidatorContext context) {

        String title = ((String) PropertyAccessorFactory)
                .forBeanPropertyAccess(obj)
                .getPropertyType(this.titleField);

    }
}
