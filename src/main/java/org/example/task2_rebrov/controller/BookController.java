package org.example.task2_rebrov.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.task2_rebrov.dto.CreateBookRequest;
import org.example.task2_rebrov.entity.Book;
import org.example.task2_rebrov.service.BookService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/books")
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;

    @GetMapping
    public ResponseEntity<List<Book>> getAllBooks() {
        List<Book> books = bookService.findAll();
        return ResponseEntity.ok(books);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Book> getBookById(@PathVariable Long id) {
        Book book = bookService.findById(id);
        return ResponseEntity.ok(book);
    }

    @PostMapping
    public ResponseEntity<Book> createBook(@Valid @RequestBody CreateBookRequest bookRequest) {
        Book createdBook = bookService.save(bookRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdBook);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Book> updateBook(@PathVariable Long id, @Valid @RequestBody CreateBookRequest bookRequest) {
        Book updatedBook = bookService.update(id, bookRequest);
        return ResponseEntity.ok(updatedBook);
    }
}