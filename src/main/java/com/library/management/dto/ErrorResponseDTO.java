package com.library.management.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ErrorResponseDTO {

	private LocalDateTime timestamp;
	
    private int status;
    
    private String message;
}
