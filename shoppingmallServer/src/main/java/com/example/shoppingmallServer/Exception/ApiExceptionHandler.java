package com.example.shoppingmallServer.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.ZoneId;
import java.time.ZonedDateTime;

@ControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(value = {EmptyValueException.class})
    public ResponseEntity<Object> handleEmptyValueExistException(EmptyValueException e) {
        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
        String errorMessage = e.getMessage();
        ApiException apiException = new ApiException(errorMessage, httpStatus, ZonedDateTime.now(ZoneId.of("Z")));
        return new ResponseEntity<>(apiException, httpStatus);
    }

    @ExceptionHandler(value = {DuplicateUserException.class})
    public ResponseEntity<Object> handleDuplicateUserException(DuplicateUserException e) {
        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
        String errorMessage = e.getMessage();
        ApiException apiException = new ApiException(errorMessage, httpStatus, ZonedDateTime.now(ZoneId.of("Z")));
        return new ResponseEntity<>(apiException, httpStatus);
    }

    @ExceptionHandler(value = {FailedJoinException.class})
    public ResponseEntity<Object> handleJoinFailException(FailedJoinException e) {
        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
        String errorMessage = e.getMessage();
        ApiException apiException = new ApiException(errorMessage, httpStatus, ZonedDateTime.now(ZoneId.of("Z")));
        return new ResponseEntity<>(apiException, httpStatus);
    }

    @ExceptionHandler(value = {PwDoesNotMatched.class})
    public ResponseEntity<Object> handlePwDoesNotMatched(PwDoesNotMatched e) {
        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
        String errorMessage = e.getMessage();
        ApiException apiException = new ApiException(errorMessage, httpStatus, ZonedDateTime.now(ZoneId.of("Z")));
        return new ResponseEntity<>(apiException, httpStatus);
    }

    @ExceptionHandler(value = {NotFoundException.class})
    public ResponseEntity<Object> handleNotFoundUserException(NotFoundException e) {
        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
        String errorMessage = e.getMessage();
        ApiException apiException = new ApiException(errorMessage, httpStatus, ZonedDateTime.now(ZoneId.of("Z")));
        return new ResponseEntity<>(apiException, httpStatus);
    }

    @ExceptionHandler(value = {FailedModifyException.class})
    public ResponseEntity<Object> handleFailedModifyException(FailedModifyException e) {
        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
        String errorMessage = e.getMessage();
        ApiException apiException = new ApiException(errorMessage, httpStatus, ZonedDateTime.now(ZoneId.of("Z")));
        return new ResponseEntity<>(apiException, httpStatus);
    }

    @ExceptionHandler(value = {FailedUploadItem.class})
    public ResponseEntity<Object> handleFailedUploadItem(FailedUploadItem e) {
        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
        String errorMessage = e.getMessage();
        ApiException apiException = new ApiException(errorMessage, httpStatus, ZonedDateTime.now(ZoneId.of("Z")));
        return new ResponseEntity<>(apiException, httpStatus);
    }

    @ExceptionHandler(value = {EmptyCategoryItem.class})
    public ResponseEntity<Object> handleEmptyCategoryItem(EmptyCategoryItem e) {
        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
        String errorMessage = e.getMessage();
        ApiException apiException = new ApiException(errorMessage, httpStatus, ZonedDateTime.now(ZoneId.of("Z")));
        return new ResponseEntity<>(apiException, httpStatus);
    }

    @ExceptionHandler(value = {DuplicateFileException.class})
    public ResponseEntity<Object> handleDuplicateFileException(DuplicateFileException e) {
        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
        String errorMessage = e.getMessage();
        ApiException apiException = new ApiException(errorMessage, httpStatus, ZonedDateTime.now(ZoneId.of("Z")));
        return new ResponseEntity<>(apiException, httpStatus);
    }
    @ExceptionHandler(value = {FailedInsertException.class})
    public ResponseEntity<Object> handleFailedInsertCart(FailedInsertException e) {
        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
        String errorMessage = e.getMessage();
        ApiException apiException = new ApiException(errorMessage, httpStatus, ZonedDateTime.now(ZoneId.of("Z")));
        return new ResponseEntity<>(apiException, httpStatus);
    }

    @ExceptionHandler(value = {FailedRemoveException.class})
    public ResponseEntity<Object> handleFailedRemoveException(FailedRemoveException e) {
        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
        String errorMessage = e.getMessage();
        ApiException apiException = new ApiException(errorMessage, httpStatus, ZonedDateTime.now(ZoneId.of("Z")));
        return new ResponseEntity<>(apiException, httpStatus);
    }
}
