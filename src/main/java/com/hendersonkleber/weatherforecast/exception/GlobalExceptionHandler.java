package com.hendersonkleber.weatherforecast.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class.getName());

    @ExceptionHandler(ResourceAlreadyExistsException.class)
    public ResponseEntity<ProblemDetail> handleResourceAlreadyExistsException(ResourceAlreadyExistsException exception) {
        var status = HttpStatus.CONFLICT;
        var problem = ProblemDetail.forStatus(status);

        problem.setTitle(status.getReasonPhrase());
        problem.setDetail(exception.getMessage());
        problem.setProperty("timestamp", LocalDateTime.now());

        logger.error(exception.getMessage(), exception);

        return ResponseEntity.status(status).body(problem);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ProblemDetail> handleResourceNotFoundException(ResourceNotFoundException exception) {
        var status = HttpStatus.NOT_FOUND;
        var problem = ProblemDetail.forStatus(status);

        problem.setTitle(status.getReasonPhrase());
        problem.setDetail(exception.getMessage());
        problem.setProperty("timestamp", LocalDateTime.now());

        logger.error(exception.getMessage(), exception);

        return ResponseEntity.status(status).body(problem);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ProblemDetail> handleException(Exception exception) {
        var status = HttpStatus.INTERNAL_SERVER_ERROR;
        var problem = ProblemDetail.forStatus(status);

        problem.setTitle(status.getReasonPhrase());
        problem.setDetail(exception.getMessage());
        problem.setProperty("timestamp", LocalDateTime.now());

        logger.error(exception.getMessage(), exception);

        return ResponseEntity.status(status).body(problem);
    }
}
