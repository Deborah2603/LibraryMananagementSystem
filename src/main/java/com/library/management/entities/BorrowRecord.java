package com.library.management.entities;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

import org.hibernate.annotations.ManyToAny;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Data;

@Data
@Entity
@Table(name ="borrow_record")
@Builder
public class BorrowRecord {
	
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	@Column(columnDefinition = "BINARY(16)")
	private UUID id;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name ="book_id", nullable =false)
	private Book book;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name ="borrower_id", nullable = false)
	private Borrower borrower;
	
	private LocalDate borrowDate;
	
	private LocalDate dueDate;
	
	private LocalDate returnDate;
	
	@Column(precision = 10, scale = 2)
	private BigDecimal fineAmount;

}
