package com.salesianostriana.dam.trianafy.error.model.impl;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.salesianostriana.dam.trianafy.error.model.ApiSubError;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

@Data
@AllArgsConstructor @NoArgsConstructor
@Builder
public class ApiValidationSubError extends ApiSubError {

    private String object;

    private String message;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String field;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Object rejectedValue;

    public static ApiValidationSubError fromObjectError(ObjectError o) {
        if(o instanceof FieldError) {
            FieldError f = (FieldError) o;

            return ApiValidationSubError
                    .builder()
                    .object(f.getObjectName())
                    .field(f.getField())
                    .rejectedValue(f.getRejectedValue())
                    .message(f.getDefaultMessage())
                    .build();
        }else {
            return ApiValidationSubError
                    .builder()
                    .object(o.getObjectName())
                    .message(o.getDefaultMessage())
                    .build();
        }
    }


}
