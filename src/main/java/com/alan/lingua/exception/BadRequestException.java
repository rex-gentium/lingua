package com.alan.lingua.exception;

import org.springframework.http.HttpStatus;

public class BadRequestException extends FormattedMessageException {
    public BadRequestException(String statusText, Object ... args) {
        super(HttpStatus.BAD_REQUEST, statusText, args);
    }
}
