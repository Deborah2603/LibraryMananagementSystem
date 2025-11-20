package com.library.management.dto;

import java.util.UUID;

import com.library.management.entities.MembershipType;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BorrowerDTO {

	private UUID id;
	
    private String name;
    
    private String email;
    
    private MembershipType membershipType;
    
    private Integer maxBorrowLimit;
    
}
