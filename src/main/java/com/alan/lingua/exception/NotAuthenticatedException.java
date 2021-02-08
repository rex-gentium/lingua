package com.alan.lingua.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpServerErrorException;

public class NotAuthenticatedException extends HttpServerErrorException {
    public NotAuthenticatedException(String statusText) {
        super(HttpStatus.UNAUTHORIZED, statusText);
    }
}
