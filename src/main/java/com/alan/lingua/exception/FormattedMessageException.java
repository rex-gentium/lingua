package com.alan.lingua.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpServerErrorException;

import java.text.MessageFormat;

public class FormattedMessageException extends HttpServerErrorException {
    public FormattedMessageException(HttpStatus statusCode, String text, Object ... args) {
        super(statusCode, MessageFormat.format(text, args));
    }
}
