package com.alan.lingua.exception;

import org.springframework.http.HttpStatus;

public class ForbiddenActionException extends FormattedMessageException {
    public ForbiddenActionException(String statusText, Object ... args) {
        super(HttpStatus.FORBIDDEN, statusText, args);
    }
}
