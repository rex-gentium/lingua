package com.alan.lingua.controller;

import com.alan.lingua.dto.response.ErrorDto;
import com.alan.lingua.exception.NotAuthenticatedException;
import com.alan.lingua.exception.NotFoundException;
import io.r2dbc.spi.R2dbcException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpStatusCodeException;

public abstract class AbstractController {
    @ExceptionHandler
    public ResponseEntity<ErrorDto> handle(NotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ErrorDto(HttpStatus.NOT_FOUND, e));
    }

    @ExceptionHandler
    public ResponseEntity<ErrorDto> handle(NotAuthenticatedException e) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(new ErrorDto(HttpStatus.UNAUTHORIZED, e));
    }

    @ExceptionHandler
    public ResponseEntity<ErrorDto> handle(R2dbcException e) throws HttpStatusCodeException {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ErrorDto(HttpStatus.INTERNAL_SERVER_ERROR, e));
    }
}
