package org.example.task2_rebrov.repository;

import org.example.task2_rebrov.entity.Borrowing;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BorrowingRepository extends JpaRepository<Borrowing, Long> {

    List<Borrowing> findByReturnedFalse();

    boolean existsByBookIdAndReturnedFalse(Long bookId);

    Optional<Borrowing> findByBookIdAndReturnedFalse(Long bookId);
}
