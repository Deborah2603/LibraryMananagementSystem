package com.library.management.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.library.management.dto.BorrowRecordDTO;
import com.library.management.dto.BorrowRequestDTO;
import com.library.management.dto.ReturnRequestDTO;
import com.library.management.service.BorrowRecordService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/library/borrow")
@Tag(name = "Borrow / Return APIs", description = "APIs for borrowing and returning books")
public class BorrowRecordController {
	
	@Autowired
	private BorrowRecordService borrowRecordService;

	@PostMapping
	@Operation(summary = "Borrow a book")
    public ResponseEntity<BorrowRecordDTO> borrowBook(@RequestBody BorrowRequestDTO request) {
		BorrowRecordDTO response = borrowRecordService.borrowBook(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/return")
    @Operation(summary = "Return a borrowed book")
    public ResponseEntity<BorrowRecordDTO> returnBook(@RequestBody ReturnRequestDTO request) {
        return ResponseEntity.ok(borrowRecordService.returnBook(request));
    }

    @GetMapping("/records/active")
    @Operation(summary = "Get active borrow records")
    public ResponseEntity<List<BorrowRecordDTO>> getActiveBorrowRecords() {
        return ResponseEntity.ok(borrowRecordService.getActiveBorrowRecords());
    }
}
