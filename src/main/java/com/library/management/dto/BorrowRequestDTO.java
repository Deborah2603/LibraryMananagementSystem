package com.library.management.dto;

import java.util.UUID;

import lombok.Data;

@Data
public class BorrowRequestDTO {

	private UUID borrowerId;
   
    private UUID bookId;
}
