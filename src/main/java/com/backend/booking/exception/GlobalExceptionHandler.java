package com.backend.booking.exception;

import com.backend.booking.exception.dto.ErrorResponseDto;
import org.apache.kafka.common.errors.ResourceNotFoundException;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponseDto> handleValidationExceptions(MethodArgumentNotValidException ex) {
        String errors;
        if (ex.getBindingResult().getFieldErrors().isEmpty()) {
            errors = ex.getBindingResult().getAllErrors()
                    .stream().map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .collect(Collectors.joining(", "));
        } else {
            errors = ex.getBindingResult().getFieldErrors()
                    .stream().map(e -> e.getField() + ": " + e.getDefaultMessage())
                    .collect(Collectors.joining(", "));
        }
        return new ResponseEntity<>(new ErrorResponseDto(errors, HttpStatus.BAD_REQUEST.value()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UsernameOrEmailAlreadyExistsException.class)
    public ResponseEntity<ErrorResponseDto> handleUsernameOrEmailAlreadyExistsException(UsernameOrEmailAlreadyExistsException ex) {
        return new ResponseEntity<>(new ErrorResponseDto(ex.getMessage(), HttpStatus.CONFLICT.value()), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(RoomUnavailableException.class)
    public ResponseEntity<ErrorResponseDto> handleRoomUnavailableException(RoomUnavailableException ex) {
        return new ResponseEntity<>(new ErrorResponseDto(ex.getMessage(), HttpStatus.UNAVAILABLE_FOR_LEGAL_REASONS.value()), HttpStatus.UNAVAILABLE_FOR_LEGAL_REASONS);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponseDto> handleNotFoundException(ResourceNotFoundException ex) {
        return new ResponseEntity<>(new ErrorResponseDto(ex.getMessage(), HttpStatus.NOT_FOUND.value()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDto> handleGenericException(Exception ex) {
        return new ResponseEntity<>(new ErrorResponseDto("An unexpected error occurred: " + ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value()), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
