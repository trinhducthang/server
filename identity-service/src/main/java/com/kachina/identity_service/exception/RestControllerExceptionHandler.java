package com.kachina.identity_service.exception;

import com.kachina.identity_service.dto.response.ApiResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Arrays;
import java.util.Date;

@ControllerAdvice
public class RestControllerExceptionHandler {

    @ExceptionHandler(RuntimeException.class)
    @ResponseBody
    public ResponseEntity<ApiResponse<Boolean>> resolveException(RuntimeException exception, HttpServletRequest request) {
        ApiResponse<Boolean> apiResponse = new ApiResponse<>();
        apiResponse.setMessage(exception.getMessage());
        apiResponse.setStatus(400);
        apiResponse.setResult(false);
        apiResponse.setPath(request.getRequestURI());
        apiResponse.setError(Arrays.toString(exception.getStackTrace()));
        apiResponse.setTimestamp(new Date());

        return new ResponseEntity<>(apiResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(AppException.class)
    @ResponseBody
    public ResponseEntity<ApiResponse<Boolean>> resolveException(AppException exception, HttpServletRequest request) {
        HttpStatus status = exception.getStatus();
        ApiResponse<Boolean> apiResponse = new ApiResponse<>();
        apiResponse.setMessage(exception.getMessage());
        apiResponse.setStatus(status.value());
        apiResponse.setResult(false);
        apiResponse.setPath(request.getRequestURI());
        apiResponse.setError(Arrays.toString(exception.getStackTrace()));
        apiResponse.setTimestamp(new Date());

        return new ResponseEntity<>(apiResponse, status);
    }

    @ExceptionHandler(NotFoundException.class)
    @ResponseBody
    public ResponseEntity<ApiResponse<Boolean>> resolveException(NotFoundException exception, HttpServletRequest request) {
        HttpStatus status = HttpStatus.NOT_FOUND;
        ApiResponse<Boolean> apiResponse = new ApiResponse<>();
        apiResponse.setMessage(exception.getMessage());
        apiResponse.setStatus(status.value());
        apiResponse.setResult(false);
        apiResponse.setPath(request.getRequestURI());
        apiResponse.setError(Arrays.toString(exception.getStackTrace()));
        apiResponse.setTimestamp(new Date());

        return new ResponseEntity<>(apiResponse, status);
    }
}
