package com.kachina.identity_service.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Data
public class AppException extends RuntimeException {

    private HttpStatus status;

    public AppException(String message) {
        this(HttpStatus.BAD_REQUEST, message);
    }

    public  AppException(HttpStatus status, String message) {
        super(message);
        this.status = status;
    }

}
