package com.alan.lingua.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpServerErrorException;

public class BadRequestException extends HttpServerErrorException {
    public BadRequestException(String statusText) {
        super(HttpStatus.BAD_REQUEST, statusText);
    }
}
