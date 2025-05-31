package org.example.task2_rebrov.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.task2_rebrov.dto.CreateBookRequest;
import org.example.task2_rebrov.entity.Author;
import org.example.task2_rebrov.entity.Book;
import org.example.task2_rebrov.repository.AuthorRepository;
import org.example.task2_rebrov.repository.BookRepository;
import org.example.task2_rebrov.service.BookService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;

    @Override
    @Transactional(readOnly = true)
    public List<Book> findAll() {
        return bookRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Book findById(Long id) {
        return bookRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Book not found with id: " + id));
    }

    @Override
    @Transactional
    public Book save(CreateBookRequest bookRequest) {
        Author author = authorRepository.findById(bookRequest.getAuthorId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST,
                        "Cannot create book: Author not found with id: " + bookRequest.getAuthorId()));

        Book book = new Book();
        book.setTitle(bookRequest.getTitle());
        book.setYear(bookRequest.getYear());
        book.setAuthor(author);
        book.setAvailable(true);

        return bookRepository.save(book);
    }

    @Override
    @Transactional
    public Book update(Long id, CreateBookRequest bookRequest) {
        Book existingBook = bookRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Book not found with id: " + id));

        existingBook.setTitle(bookRequest.getTitle());
        existingBook.setYear(bookRequest.getYear());

        // Update author only if the ID in the request is different
        if (!existingBook.getAuthor().getId().equals(bookRequest.getAuthorId())) {
            Author newAuthor = authorRepository.findById(bookRequest.getAuthorId())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST,
                            "Cannot update book: Author not found with id: " + bookRequest.getAuthorId()));
            existingBook.setAuthor(newAuthor);
        }


        return bookRepository.save(existingBook);
    }
}
