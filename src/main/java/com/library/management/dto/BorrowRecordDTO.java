package com.library.management.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BorrowRecordDTO {

	private UUID id;

	private UUID bookId;

	private UUID borrowerId;

	private String bookTitle;

	private String borrowerName;

	private LocalDate borrowDate;

	private LocalDate dueDate;

	private LocalDate returnDate;

	private BigDecimal fineAmount;

}
