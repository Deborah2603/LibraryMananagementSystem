package com.library.management.dto;

import java.util.UUID;

import lombok.Data;

@Data
public class BookDTO {

	private UUID id;
	
	private String title;
	
	private String author;
	
	private String category;
	
	private boolean available;
	
	private Integer totalCopies;
	
	private Integer availableCopies;

}
