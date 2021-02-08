package com.alan.lingua.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.time.Instant;

@Data
public class ErrorDto {
    private Instant timestamp;
    private Integer status;
    private String error;
    private String message;

    public ErrorDto(HttpStatus status, Exception e) {
        this.timestamp = Instant.now();
        this.status = status.value();
        this.error = e.getClass().getSimpleName();
        this.message = e.getMessage();
    }
}
