package org.example.task2_rebrov.repository;

import org.example.task2_rebrov.entity.Borrowing;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BorrowingRepository extends JpaRepository<Borrowing, Long> {

    // Find all records where the book is not returned
    List<Borrowing> findByReturnedFalse();

    // Check if an active borrowing exists for a specific book
    boolean existsByBookIdAndReturnedFalse(Long bookId);

    // Find an active borrowing by book ID
    Optional<Borrowing> findByBookIdAndReturnedFalse(Long bookId);
}
