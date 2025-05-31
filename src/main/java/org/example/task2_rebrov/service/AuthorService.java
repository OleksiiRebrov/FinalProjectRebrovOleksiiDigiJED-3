package org.example.task2_rebrov.service;

import org.example.task2_rebrov.dto.CreateAuthorRequest;
import org.example.task2_rebrov.entity.Author;

import java.util.List;

public interface AuthorService {
    List<Author> findAll();
    Author findById(Long id);
    Author save(CreateAuthorRequest authorRequest);
}
