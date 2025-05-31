package org.example.task2_rebrov.service;

import org.example.task2_rebrov.dto.CreateBorrowingRequest;
import org.example.task2_rebrov.entity.Borrowing;

import java.util.List;

public interface BorrowingService {
    List<Borrowing> findActiveBorrowings();
    Borrowing borrowBook(CreateBorrowingRequest borrowingRequest);
    Borrowing returnBook(Long borrowingId);
}
