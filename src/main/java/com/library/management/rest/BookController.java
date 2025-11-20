package com.library.management.rest;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.library.management.dto.BookDTO;
import com.library.management.service.BookService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/library/books")
@Tag(name ="Books", description ="Book Management APIs")
public class BookController {

	@Autowired
	private BookService bookService;

	@Operation(summary = "Add or update book", description = "Creates a new book or increases stock if it already exists")
	@PostMapping
	public ResponseEntity<BookDTO> createOrUpdateBook(@RequestBody BookDTO request) {
		BookDTO response = bookService.addOrUpdate(request);
		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}

	@Operation(summary = "Get books with filters",
            description = "Filter by category, availability, pagination and sorting")
	@GetMapping
	public ResponseEntity<Page<BookDTO>> getBooks(
			@RequestParam(required = false) String category,
			@RequestParam(required = false) Boolean available,
			@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int size,
			@RequestParam(defaultValue = "title") String sortBy,
			@RequestParam(defaultValue = "asc") String dir) {
		return ResponseEntity.ok(bookService.getBooks(category, available, page, size, sortBy, dir));
	}

	@Operation(summary = "Update book details")
	@PutMapping("/{id}")
	public ResponseEntity<BookDTO> updateBook(@PathVariable UUID id, @RequestBody BookDTO request) {
		return ResponseEntity.ok(bookService.updateBook(id, request));
	}

	@Operation(summary = "Soft delete a book")
	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteBook(@PathVariable UUID id) {
		bookService.deleteBook(id);
		return ResponseEntity.ok("Book deleted successfully");
	}


}
