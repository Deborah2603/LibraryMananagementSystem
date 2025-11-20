package com.library.management.validation;

import org.springframework.stereotype.Component;

import com.library.management.dto.BookDTO;
import com.library.management.exception.BusinessException;

@Component
public class BookRequestValidator {

	public void validate(BookDTO request) {

		// Title validation
		if (request.getTitle() == null || request.getTitle().isBlank()) {
			throw new BusinessException("Title is required and cannot be blank");
		}

		// Author validation
		if (request.getAuthor() == null || request.getAuthor().isBlank()) {
			throw new BusinessException("Author is required and cannot be blank");
		}

		// Category validation
		if (request.getCategory() == null || request.getCategory().isBlank()) {
			throw new BusinessException("Category is required");
		}

		// Total copies
		if (request.getTotalCopies() == null || request.getTotalCopies() < 1) {
			throw new BusinessException("Total copies must be at least 1");
		}

		// Available copies
		if (request.getAvailableCopies() == null || request.getAvailableCopies() < 0) {
			throw new BusinessException("Available copies cannot be negative");
		}

		// Available <= Total check
		if (request.getAvailableCopies() > request.getTotalCopies()) {
			throw new BusinessException("Available copies cannot be greater than total copies");
		}
	}
}