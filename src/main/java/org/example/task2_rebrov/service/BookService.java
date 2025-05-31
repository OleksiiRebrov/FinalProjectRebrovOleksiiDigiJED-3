package org.example.task2_rebrov.service;

import org.example.task2_rebrov.dto.CreateBookRequest;
import org.example.task2_rebrov.entity.Book;

import java.util.List;

public interface BookService {
    List<Book> findAll();
    Book findById(Long id);
    Book save(CreateBookRequest bookRequest);
    Book update(Long id, CreateBookRequest bookRequest);
}
