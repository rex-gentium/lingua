package com.alan.lingua.exception;

import org.springframework.http.HttpStatus;

public class NotAuthenticatedException extends FormattedMessageException {
    public NotAuthenticatedException(String statusText, Object ... args) {
        super(HttpStatus.UNAUTHORIZED, statusText, args);
    }
}
