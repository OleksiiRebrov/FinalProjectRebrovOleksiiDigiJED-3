package org.example.task2_rebrov.service.impl;


import lombok.RequiredArgsConstructor;
import org.example.task2_rebrov.dto.CreateBorrowingRequest;
import org.example.task2_rebrov.entity.Book;
import org.example.task2_rebrov.entity.Borrowing;
import org.example.task2_rebrov.repository.BookRepository;
import org.example.task2_rebrov.repository.BorrowingRepository;
import org.example.task2_rebrov.service.BorrowingService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BorrowingServiceImpl implements BorrowingService {

    private final BorrowingRepository borrowingRepository;
    private final BookRepository bookRepository;

    @Override
    @Transactional(readOnly = true)
    public List<Borrowing> findActiveBorrowings() {
        return borrowingRepository.findByReturnedFalse();
    }

    @Override
    @Transactional
    public Borrowing borrowBook(CreateBorrowingRequest borrowingRequest) {
        Book book = bookRepository.findById(borrowingRequest.getBookId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Cannot borrow: Book not found with id: " + borrowingRequest.getBookId()));

        if (!book.isAvailable()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Book with id " + book.getId() + " is currently not available.");
        }


        if (borrowingRepository.existsByBookIdAndReturnedFalse(book.getId())) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Data inconsistency detected for book " + book.getId() + ": marked available but already borrowed.");
        }

        Borrowing borrowing = new Borrowing();
        borrowing.setBorrowerName(borrowingRequest.getBorrowerName());
        borrowing.setBook(book);
        borrowing.setBorrowedDate(LocalDate.now());
        borrowing.setReturned(false);

        book.setAvailable(false);

        bookRepository.save(book);
        return borrowingRepository.save(borrowing);
    }

    @Override
    @Transactional
    public Borrowing returnBook(Long borrowingId) {
        Borrowing borrowing = borrowingRepository.findById(borrowingId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Borrowing record not found with id: " + borrowingId));

        if (borrowing.isReturned()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Book for this borrowing record (id: " + borrowingId + ") has already been returned.");
        }

        Book book = borrowing.getBook();
        if (book == null) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Data inconsistency: Borrowing record " + borrowingId + " missing associated book.");
        }

        borrowing.setReturned(true);

        book.setAvailable(true);

        bookRepository.save(book);
        return borrowingRepository.save(borrowing);
    }
}