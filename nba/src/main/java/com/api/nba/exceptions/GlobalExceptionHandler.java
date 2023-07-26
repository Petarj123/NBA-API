package com.api.nba.exceptions;

import com.api.nba.DTO.ExceptionResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(InvalidConferenceException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ExceptionResponse> handleInvalidConferenceException(InvalidConferenceException ex) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        ExceptionResponse error = new ExceptionResponse(status.value(), status.getReasonPhrase(), ex.getMessage());
        return new ResponseEntity<>(error, status);
    }
    @ExceptionHandler(InvalidDivisionException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ExceptionResponse> handleInvalidDivisionException(InvalidDivisionException ex) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        ExceptionResponse error = new ExceptionResponse(status.value(), status.getReasonPhrase(), ex.getMessage());
        return new ResponseEntity<>(error, status);
    }
    @ExceptionHandler(InvalidSeasonException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ExceptionResponse> handleInvalidSeasonException(InvalidSeasonException ex) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        ExceptionResponse error = new ExceptionResponse(status.value(), status.getReasonPhrase(), ex.getMessage());
        return new ResponseEntity<>(error, status);
    }
    @ExceptionHandler(IllegalAbbreviationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ExceptionResponse> handleIllegalAbbreviationException(IllegalAbbreviationException ex) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        ExceptionResponse error = new ExceptionResponse(status.value(), status.getReasonPhrase(), ex.getMessage());
        return new ResponseEntity<>(error, status);
    }
    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ExceptionResponse> handleIllegalArgumentException(IllegalArgumentException ex) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        ExceptionResponse error = new ExceptionResponse(status.value(), status.getReasonPhrase(), ex.getMessage());
        return new ResponseEntity<>(error, status);
    }
    @ExceptionHandler(InvalidPlayerCountException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ExceptionResponse> handleInvalidPlayerCountException(InvalidPlayerCountException ex) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        ExceptionResponse error = new ExceptionResponse(status.value(), status.getReasonPhrase(), ex.getMessage());
        return new ResponseEntity<>(error, status);
    }
}
