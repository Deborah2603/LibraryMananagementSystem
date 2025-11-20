package com.library.management.entities;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Data;

@Data
@Entity
@Table(name ="book")
@Builder
public class Book {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	@Column(columnDefinition = "BINARY(16)")
	private UUID id;

	private String title;

	private String author;

	private String category;

	@Column(name ="is_available")
	private boolean available;

	@Column(name ="total_copies")
	private Integer totalCopies;

	@Column(name ="available_copies")
	private Integer availableCopies;	

	private boolean deleted = false;
	
	@OneToMany(mappedBy = "book", fetch= FetchType.LAZY)
	private List<BorrowRecord> borrowRecords = new ArrayList<>();
}
