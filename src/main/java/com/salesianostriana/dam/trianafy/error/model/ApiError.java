package com.salesianostriana.dam.trianafy.error.model;


import com.salesianostriana.dam.trianafy.error.model.impl.ApiErrorImpl;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public interface ApiError {

    HttpStatus getStatus();

    int getStatusCode();

    String getMessage();

    String getPath();

    LocalDateTime getDate();

    List<ApiSubError> getSubErrors();

    static ApiError fromErrorAttributes(Map<String, Object> defaultErrorAttributesMap) {

        int statusCode = ((Integer)defaultErrorAttributesMap.get("status")).intValue();

        ApiErrorImpl result = ApiErrorImpl
                .builder()
                .status(HttpStatus.valueOf(statusCode))
                .message((String) defaultErrorAttributesMap.getOrDefault("message", "No message available"))
                .path((String) defaultErrorAttributesMap.getOrDefault("path", "No path available"))
                .build();

        // FALTA TERMINARLA
        return null;
    }




}
