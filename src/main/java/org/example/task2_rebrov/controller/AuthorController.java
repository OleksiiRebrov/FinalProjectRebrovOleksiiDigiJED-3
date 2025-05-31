package org.example.task2_rebrov.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.task2_rebrov.dto.CreateAuthorRequest;
import org.example.task2_rebrov.entity.Author;
import org.example.task2_rebrov.service.AuthorService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/authors")
@RequiredArgsConstructor
public class AuthorController {

    private final AuthorService authorService;

    @GetMapping
    public ResponseEntity<List<Author>> getAllAuthors() {
        List<Author> authors = authorService.findAll();
        return ResponseEntity.ok(authors);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Author> getAuthorById(@PathVariable Long id) {
        Author author = authorService.findById(id);
        return ResponseEntity.ok(author);
    }

    @PostMapping
    public ResponseEntity<Author> createAuthor(@Valid @RequestBody CreateAuthorRequest authorRequest) {
        Author createdAuthor = authorService.save(authorRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdAuthor);
    }
}
