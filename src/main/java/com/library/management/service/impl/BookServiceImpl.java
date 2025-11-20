package com.library.management.service.impl;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.library.management.dto.BookDTO;
import com.library.management.entities.Book;
import com.library.management.exception.BusinessException;
import com.library.management.repository.BookRepository;
import com.library.management.repository.BorrowRecordRepository;
import com.library.management.service.BookService;
import com.library.management.validation.BookRequestValidator;

import jakarta.transaction.Transactional;

@Service
public class BookServiceImpl implements BookService{

	@Autowired
	private BookRepository bookRepo;
	
	@Autowired
	private BorrowRecordRepository borrowRecordRepo;
	
	@Autowired
	private BookRequestValidator validator;
	
	@Override
	@Transactional
	public BookDTO addOrUpdate(BookDTO dto) {
		validator.validate(dto);
		if(bookRepo.existsByTitleAndDeletedFalse(dto.getTitle())) {
			Book existingBook = bookRepo.findByTitleAndDeletedFalse(dto.getTitle());
			existingBook.setTotalCopies(existingBook.getTotalCopies()+ dto.getTotalCopies());
			existingBook.setAvailableCopies(existingBook.getAvailableCopies() + dto.getAvailableCopies());
			bookRepo.save(existingBook);
			return mapToDto(existingBook);
		}
		else {
			Book book = Book.builder()
					.title(dto.getTitle())
					.author(dto.getAuthor())
					.category(dto.getCategory())
					.totalCopies(dto.getTotalCopies())
					.availableCopies(dto.getAvailableCopies())
					.deleted(false)
					.build();

			Book saved = bookRepo.save(book);
			return mapToDto(saved);
		}
	}

	@Override
	public Page<BookDTO> getBooks(String category, Boolean available, int page, int size, String sortBy, String order) {
		Sort.Direction dir = "desc".equalsIgnoreCase(order) ? Sort.Direction.DESC : Sort.Direction.ASC;
		Pageable pageable = PageRequest.of(page, size, Sort.by(dir, sortBy));
		Page<Book> bookPage = bookRepo.findBooksByCategoryAndAvailability(category, available, pageable);
		return bookPage.map(this::mapToDto);
	}

	@Override
	@Transactional
	public BookDTO updateBook(UUID id, BookDTO dto) {
		if(id==null) {
			throw new BusinessException("Id Cannot be null");		
		}
		Book book =  bookRepo.findById(id).orElseThrow();
		book.setTitle(dto.getTitle());
		book.setAuthor(dto.getAuthor());
		book.setCategory(dto.getCategory());
		book.setTotalCopies(dto.getTotalCopies());
		if(book.getAvailableCopies()> book.getTotalCopies()) {
			book.setAvailableCopies(dto.getTotalCopies());
		}
		book.setAvailable(book.getAvailableCopies() > 0);
		Book updated = bookRepo.save(book);
		return mapToDto(updated);
	}

	@Override
	@Transactional
	public void deleteBook(UUID id) {
		boolean hasActiveBorrow = borrowRecordRepo.existsByBookIdAndReturnDateIsNull(id);
		if(hasActiveBorrow) {
			throw new BusinessException("Cannot delete the book. It has active borrow records.");
		}
        Book book = bookRepo.findById(id).orElseThrow();
        book.setDeleted(true);
        book.setAvailable(false);
        book.setAvailableCopies(0);
                
        bookRepo.save(book);
		
	}
	
	private BookDTO mapToDto(Book b) {
		BookDTO d = new BookDTO();
		d.setId(b.getId());
		d.setTitle(b.getTitle());
		d.setAuthor(b.getAuthor());
		d.setCategory(b.getCategory());
		d.setTotalCopies(b.getTotalCopies());
		d.setAvailableCopies(b.getAvailableCopies());
		return d;
	}

}
