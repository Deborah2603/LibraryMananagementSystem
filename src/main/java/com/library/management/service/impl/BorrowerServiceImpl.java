package com.library.management.service.impl;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.library.management.dto.BorrowRecordDTO;
import com.library.management.dto.BorrowerDTO;
import com.library.management.entities.BorrowRecord;
import com.library.management.entities.Borrower;
import com.library.management.exception.ResourceNotFoundException;
import com.library.management.repository.BorrowRecordRepository;
import com.library.management.repository.BorrowRepository;
import com.library.management.service.BorrowerService;

import jakarta.transaction.Transactional;

@Service
public class BorrowerServiceImpl implements BorrowerService{

	@Autowired
	private BorrowRepository borrowerRepository;
	
	@Autowired
    private BorrowRecordRepository borrowRecordRepository;
    
	@Override
	@Transactional
	public BorrowerDTO registerBorrower(BorrowerDTO dto) {
		Borrower borrower = Borrower.builder()
                .name(dto.getName())
                .email(dto.getEmail())
                .membershipType(dto.getMembershipType())
                .maxBorrowLimit(dto.getMembershipType() == com.library.management.entities.MembershipType.BASIC ? 2 : 5)
                .build();
        return mapToDTOs(borrowerRepository.save(borrower));
	}

	@Override
	public List<BorrowRecordDTO> getBorrowHistory(UUID borrowerId) {
	    if (!borrowerRepository.existsById(borrowerId)) {
	        throw new ResourceNotFoundException("Borrower not found");
	    }

	    return borrowRecordRepository.findByBorrowerId(borrowerId) // List<BorrowRecord>
	            .stream()
	            .map(this::mapBorrowRecordToDTO) //Converts entity -> DTO
	            .toList();
	}

	@Override
	public List<BorrowerDTO> getOverdueBorrowers() {
		return borrowRecordRepository
				.findByDueDateBeforeAndReturnDateIsNull(LocalDate.now())
				.stream()
				.map(record -> record.getBorrower())
				.distinct()
				.map(this::mapToDTOs)
				.toList();
	}
	
	  // Private mapping method for BorrowRecord → BorrowRecordDTO
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

    // Private mapping method for Borrower → BorrowerDTO
    private BorrowerDTO mapToDTOs(Borrower borrower) {
        return BorrowerDTO.builder()
                .id(borrower.getId())
                .name(borrower.getName())
                .email(borrower.getEmail())
                .membershipType(borrower.getMembershipType())
                .maxBorrowLimit(borrower.getMaxBorrowLimit())
                .build();
    }

}
