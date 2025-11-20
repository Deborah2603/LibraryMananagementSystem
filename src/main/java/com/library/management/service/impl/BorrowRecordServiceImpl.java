package com.library.management.service.impl;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.library.management.dto.BorrowRecordDTO;
import com.library.management.dto.BorrowRequestDTO;
import com.library.management.dto.ReturnRequestDTO;
import com.library.management.entities.Book;
import com.library.management.entities.BorrowRecord;
import com.library.management.entities.Borrower;
import com.library.management.exception.BusinessException;
import com.library.management.exception.ResourceNotFoundException;
import com.library.management.repository.BookRepository;
import com.library.management.repository.BorrowRecordRepository;
import com.library.management.repository.BorrowRepository;
import com.library.management.service.BorrowRecordService;

import jakarta.transaction.Transactional;

@Service
public class BorrowRecordServiceImpl implements BorrowRecordService{

	@Autowired
	private BorrowRepository borrowerRepository;

	@Autowired
	private BookRepository bookRepository;

	@Autowired
	private BorrowRecordRepository borrowRecordRepository;

	@Transactional
	@Override
	public BorrowRecordDTO borrowBook(BorrowRequestDTO request) {
		Borrower borrower = borrowerRepository.findById(request.getBorrowerId())
				.orElseThrow(() -> new ResourceNotFoundException("Borrower not found"));

		Book book = bookRepository.findById(request.getBookId())
				.orElseThrow(() -> new ResourceNotFoundException("Book not found"));

		// Book availability validation
		if (book.getAvailableCopies() < 1) {
			  throw new BusinessException("No copies available to borrow");
		}

		// Max limit validation
		long activeBorrowCount = borrowRecordRepository.countByBorrowerIdAndReturnDateIsNull(request.getBorrowerId());
		if (activeBorrowCount >= borrower.getMaxBorrowLimit()) {
			 throw new BusinessException("Max borrow limit reached");
		}

		// Create borrow record
		BorrowRecord record = BorrowRecord.builder()
				.book(book)
				.borrower(borrower)
				.borrowDate(LocalDate.now())
				.dueDate(LocalDate.now().plusDays(14))
				.build();

		// Reduce available copies
		book.setAvailableCopies(book.getAvailableCopies() - 1);
		bookRepository.save(book);

		return mapBorrowRecordToDTO(borrowRecordRepository.save(record));
	}

	@Transactional
	@Override
	public BorrowRecordDTO returnBook(ReturnRequestDTO request) {
		BorrowRecord record = borrowRecordRepository
				.findByBorrowerIdAndBookIdAndReturnDateIsNull(request.getBorrowerId(), request.getBookId())
				.orElseThrow(() -> new BusinessException("No active borrow record found"));

		record.setReturnDate(LocalDate.now());

		// Fine calculation
		if (record.getReturnDate().isAfter(record.getDueDate())) {
			long daysLate = ChronoUnit.DAYS.between(record.getDueDate(), record.getReturnDate());
			double fineRate = 10; // Default or from FinePolicy or take from properties file
			Double value = daysLate * fineRate;
			record.setFineAmount(BigDecimal.valueOf(value));
		}

		// Increase available copies
		Book book = record.getBook();
		book.setAvailableCopies(book.getAvailableCopies() + 1);
		bookRepository.save(book);

		return mapBorrowRecordToDTO(borrowRecordRepository.save(record));
	}

	@Override
	public List<BorrowRecordDTO> getActiveBorrowRecords() {
		return borrowRecordRepository.findByReturnDateIsNull()
				.stream()
				.map(this::mapBorrowRecordToDTO)
				.toList();
	}



	private BorrowRecordDTO mapBorrowRecordToDTO(BorrowRecord record) {
		return BorrowRecordDTO.builder()
				.id(record.getId())
				.bookId(record.getBook().getId())
				.bookTitle(record.getBook().getTitle())
				.borrowerId(record.getBorrower().getId())
				.borrowerName(record.getBorrower().getName())
				.borrowDate(record.getBorrowDate())
				.dueDate(record.getDueDate())
				.returnDate(record.getReturnDate())
				.fineAmount(record.getFineAmount())
				.build();
	}

}
