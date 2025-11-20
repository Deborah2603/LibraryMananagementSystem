package com.library.management.service;

import java.util.UUID;

import org.springframework.data.domain.Page;

import com.library.management.dto.BookDTO;

public interface BookService {
	
	public BookDTO addOrUpdate(BookDTO dto);
	
	public Page<BookDTO> getBooks(String category, Boolean available, int page, int size, String sortBy, String order);
	
	public BookDTO updateBook(UUID id, BookDTO dto);
	
	public void deleteBook(UUID id);

}
