package com.library.management.dto;

import java.util.UUID;

import lombok.Data;

@Data
public class ReturnRequestDTO {

	private UUID borrowerId;

    private UUID bookId;
}
