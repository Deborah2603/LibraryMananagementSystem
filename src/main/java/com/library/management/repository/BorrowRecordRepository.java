package com.library.management.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.library.management.entities.BorrowRecord;

@Repository
public interface BorrowRecordRepository extends JpaRepository<BorrowRecord, UUID>{

	public boolean existsByBookIdAndReturnDateIsNull(UUID borrowId);

	public List<BorrowRecord> findByBorrowerId(UUID borrowerId);

	public List<BorrowRecord> findByDueDateBeforeAndReturnDateIsNull(LocalDate now);

	public Optional<BorrowRecord> findByBorrowerIdAndBookIdAndReturnDateIsNull(UUID borrowerId, UUID bookId);

	public List<BorrowRecord> findByReturnDateIsNull();

	public long countByBorrowerIdAndReturnDateIsNull(UUID borrowerId);
}
