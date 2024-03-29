package com.salesianostriana.dam.trianafy.error;

import com.salesianostriana.dam.trianafy.error.model.ApiError;
import com.salesianostriana.dam.trianafy.error.model.impl.ApiErrorImpl;
import com.salesianostriana.dam.trianafy.error.model.impl.ApiValidationSubError;
import com.salesianostriana.dam.trianafy.exception.*;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import org.hibernate.validator.internal.engine.path.PathImpl;

import javax.persistence.EntityNotFoundException;
import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalRestControllerAdvice extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers, HttpStatus status, WebRequest request) {
        return buildApiError(ex, request, status);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        return buildApiErrorWithSubErrors("Error de validación. Compruebe la sublista", request, status, ex.getAllErrors());
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        return buildApiError(ex, request, status);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<?> handleConstraintViolationException(ConstraintViolationException ex, WebRequest request) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(
                        ApiErrorImpl
                                .builder()
                                .status(HttpStatus.BAD_REQUEST)
                                .message("Error de violación de restricción. Compruebe la sublista")
                                .path(((ServletWebRequest) request).getRequest().getRequestURI())
                                .subErrors(
                                        ex.getConstraintViolations()
                                                .stream()
                                                .map(
                                                        v -> {
                                                            return ApiValidationSubError
                                                                    .builder()
                                                                    .message(v.getMessage())
                                                                    .rejectedValue(v.getInvalidValue())
                                                                    .object(v.getRootBean().getClass().getSimpleName())
                                                                    .field(((PathImpl) v.getPropertyPath()).getLeafNode().asString())
                                                                    .build();
                                                        }
                                                )
                                                .collect(Collectors.toList())
                                )
                                .build()
                );
    }

    @ExceptionHandler({
            ArtistNotFoundException.class,
            EmptyArtistListException.class,
            EmptySongListException.class,
            SongNotFoundException.class,
            EmptyPlaylistException.class,
            PlaylistNotFoundException.class
    })
    public ResponseEntity<?> handleNotFoundException(EntityNotFoundException ex, WebRequest request) {

        return buildApiError(ex, request, HttpStatus.NOT_FOUND);

    }

    @ExceptionHandler(ArtistHasSongsException.class)
    public ResponseEntity<?> handleArtistHasSongsException(ArtistHasSongsException ex, WebRequest request) {
        return buildApiError(ex, request, HttpStatus.BAD_REQUEST);
    }


    private final ResponseEntity<Object> buildApiError(Exception ex, WebRequest request, HttpStatus status) {
        return ResponseEntity
                .status(status)
                .body(ApiErrorImpl
                        .builder()
                        .status(status)
                        .message(ex.getMessage())
                        .path(((ServletWebRequest) request).getRequest().getRequestURI())
                        .build());
    }

    private final ResponseEntity<Object> buildApiErrorWithSubErrors(String message, WebRequest request, HttpStatus status, List<ObjectError> subErrors) {
        return ResponseEntity
                .status(status)
                .body(ApiErrorImpl
                        .builder()
                        .status(status)
                        .message(message)
                        .path(((ServletWebRequest) request).getRequest().getRequestURI())
                        .subErrors(
                                subErrors
                                        .stream()
                                        .map(ApiValidationSubError::fromObjectError)
                                        .collect(Collectors.toList())
                        )
                        .build());
    }
}
