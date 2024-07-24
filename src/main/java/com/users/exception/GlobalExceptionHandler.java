package com.users.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


@ControllerAdvice
public class GlobalExceptionHandler {

	 @ExceptionHandler(UserNotFoundException.class)
	    public ResponseEntity<String> handleUserNotFoundException(UserNotFoundException ex) {
	        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ex.getMessage());
	    }

	    @ExceptionHandler(InvalidPasswordException.class)
	    public ResponseEntity<String> handleInvalidPasswordException(InvalidPasswordException ex) {
	        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ex.getMessage());
	    }

	    @ExceptionHandler(RuntimeException.class)
	    public ResponseEntity<String> handleRuntimeException(RuntimeException ex) {
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred: " + ex.getMessage());
	    }
}
