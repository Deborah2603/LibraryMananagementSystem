package com.library.management.service;

import java.util.List;

import com.library.management.dto.BorrowRecordDTO;
import com.library.management.dto.BorrowRequestDTO;
import com.library.management.dto.ReturnRequestDTO;

public interface BorrowRecordService {

	public BorrowRecordDTO borrowBook(BorrowRequestDTO request);

	public BorrowRecordDTO returnBook(ReturnRequestDTO request);
	
	public List<BorrowRecordDTO> getActiveBorrowRecords();
}
