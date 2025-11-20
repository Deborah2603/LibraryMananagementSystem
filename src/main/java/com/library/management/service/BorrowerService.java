package com.library.management.service;

import java.util.List;
import java.util.UUID;

import com.library.management.dto.BorrowRecordDTO;
import com.library.management.dto.BorrowerDTO;

public interface BorrowerService {

	public BorrowerDTO registerBorrower(BorrowerDTO dto);

	public List<BorrowRecordDTO> getBorrowHistory(UUID borrowerId);

	public List<BorrowerDTO> getOverdueBorrowers();
}
