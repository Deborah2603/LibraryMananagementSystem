package com.library.management.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.library.management.entities.Borrower;

@Repository
public interface BorrowRepository extends JpaRepository<Borrower, UUID> {

}
