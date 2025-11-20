package com.library.management.rest;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.library.management.dto.BorrowRecordDTO;
import com.library.management.dto.BorrowerDTO;
import com.library.management.service.BorrowerService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/library/borrowers")
@Tag(name = "Borrowers", description = "Borrower Management APIs")
public class BorrowerController {
	
	@Autowired
	private BorrowerService borrowerService;
	
	@PostMapping
	@Operation(summary = "Register new borrower")
    public ResponseEntity<BorrowerDTO> registerBorrower(@RequestBody BorrowerDTO request) {
		BorrowerDTO response = borrowerService.registerBorrower(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}/records")
    @Operation(summary = "Get borrow history of borrower")
    public ResponseEntity<List<BorrowRecordDTO>> getBorrowerHistory(@PathVariable UUID id) {
        return ResponseEntity.ok(borrowerService.getBorrowHistory(id));
    }

    @GetMapping("/overdue")
    @Operation(summary = "Get borrowers with overdue books")
    public ResponseEntity<List<BorrowerDTO>> getOverdueBorrowers() {
        return ResponseEntity.ok(borrowerService.getOverdueBorrowers());
    }
    
}
