package com.library.management.exception;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.library.management.dto.ErrorResponseDTO;

@RestControllerAdvice
public class GlobalExceptionHandler {

	// Handle ResourceNotFoundException (404)
	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<ErrorResponseDTO> handleResourceNotFound(ResourceNotFoundException ex) {
		ErrorResponseDTO error = new ErrorResponseDTO(
				LocalDateTime.now(),
				HttpStatus.NOT_FOUND.value(),
				ex.getMessage()
				);
		return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
	}

	// Handle BusinessException (400 - Bad Request)
	@ExceptionHandler(BusinessException.class)
	public ResponseEntity<ErrorResponseDTO> handleBusinessException(BusinessException ex) {
		ErrorResponseDTO error = new ErrorResponseDTO(
				LocalDateTime.now(),
				HttpStatus.BAD_REQUEST.value(),
				ex.getMessage()
				);
		return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
	}

	// Handle Validation Exceptions (DTO validation)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Map<String, String>> handleValidationException(MethodArgumentNotValidException ex) {
		Map<String, String> errors = new HashMap<>();

		ex.getBindingResult().getAllErrors().forEach(error -> {
			String fieldName = ((FieldError) error).getField();
			String message = error.getDefaultMessage();
			errors.put(fieldName, message);
		});

		return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
	}

	// Handle Generic Exceptions (500 - Internal Server Error)
	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorResponseDTO> handleGeneralException(Exception ex) {
		ErrorResponseDTO error = new ErrorResponseDTO(
				LocalDateTime.now(),
				HttpStatus.INTERNAL_SERVER_ERROR.value(),
				"Something went wrong. Please try again later."
				);
		ex.printStackTrace();
		return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
	}
}

