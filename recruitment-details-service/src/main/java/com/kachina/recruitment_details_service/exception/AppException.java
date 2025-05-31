package com.kachina.recruitment_details_service.exception;

import lombok.Data;
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
