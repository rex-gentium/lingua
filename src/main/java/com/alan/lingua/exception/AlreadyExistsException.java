package com.alan.lingua.exception;

import org.springframework.http.HttpStatus;

public class AlreadyExistsException extends FormattedMessageException {
    public AlreadyExistsException(String statusText, Object ... args) {
        super(HttpStatus.BAD_REQUEST, statusText, args);
    }
}
