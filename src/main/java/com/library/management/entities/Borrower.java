package com.library.management.entities;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
@Table(name ="borrower")
@Builder
public class Borrower {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	@Column(columnDefinition = "BINARY(16)")
	private UUID id;

	private String name;

	@Column(unique=true)
	private String email;

	@Column(name ="membership_type")
	@Enumerated(EnumType.STRING)
	private MembershipType membershipType;

	@Column(name ="max_borrow_limit")
	private Integer maxBorrowLimit;
	
	@OneToMany(mappedBy = "borrower", fetch = FetchType.LAZY)
	private List<BorrowRecord> borrowRecords = new ArrayList<>();

}
