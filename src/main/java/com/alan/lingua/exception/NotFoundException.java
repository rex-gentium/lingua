package com.alan.lingua.exception;

import org.springframework.http.HttpStatus;

public class NotFoundException extends FormattedMessageException {
    public NotFoundException(String statusText, Object ... args) {
        super(HttpStatus.NOT_FOUND, statusText, args);
    }
}
