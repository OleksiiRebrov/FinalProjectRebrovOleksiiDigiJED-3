package org.example.task2_rebrov.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.task2_rebrov.dto.CreateBorrowingRequest;
import org.example.task2_rebrov.entity.Borrowing;
import org.example.task2_rebrov.service.BorrowingService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/borrowings")
@RequiredArgsConstructor
public class BorrowingController {

    private final BorrowingService borrowingService;

    @GetMapping("/active")
    public ResponseEntity<List<Borrowing>> getActiveBorrowings() {
        List<Borrowing> activeBorrowings = borrowingService.findActiveBorrowings();
        return ResponseEntity.ok(activeBorrowings);
    }

    @PostMapping
    public ResponseEntity<Borrowing> borrowBook(@Valid @RequestBody CreateBorrowingRequest borrowingRequest) {
        Borrowing borrowing = borrowingService.borrowBook(borrowingRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(borrowing);
    }

    @PutMapping("/{id}/return")
    public ResponseEntity<Borrowing> returnBook(@PathVariable Long id) {
        Borrowing returnedBorrowing = borrowingService.returnBook(id);
        return ResponseEntity.ok(returnedBorrowing);
    }
}