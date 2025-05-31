package org.example.task2_rebrov.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.task2_rebrov.dto.CreateAuthorRequest;
import org.example.task2_rebrov.entity.Author;
import org.example.task2_rebrov.repository.AuthorRepository;
import org.example.task2_rebrov.service.AuthorService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepository authorRepository;

    @Override
    @Transactional(readOnly = true)
    public List<Author> findAll() {
        return authorRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Author findById(Long id) {
        return authorRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Author not found with id: " + id));
    }

    @Override
    @Transactional
    public Author save(CreateAuthorRequest authorRequest) {
        Author author = new Author();
        author.setName(authorRequest.getName());
        author.setCountry(authorRequest.getCountry());
        return authorRepository.save(author);
    }
}