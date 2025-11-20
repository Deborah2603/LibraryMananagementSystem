package com.library.management.repository;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.library.management.entities.Book;

@Repository
public interface BookRepository extends JpaRepository<Book, UUID> {

	boolean existsByTitleAndDeletedFalse(String title);

	Book findByTitleAndDeletedFalse(String title);

	@Query("SELECT b FROM Book b WHERE b.deleted=false AND (:category IS NULL OR b.category=:category) AND (:available IS NULL OR b.available =:available)")
	Page<Book> findBooksByCategoryAndAvailability(@Param("category")String category, @Param("available") Boolean available, Pageable pageable);

}
