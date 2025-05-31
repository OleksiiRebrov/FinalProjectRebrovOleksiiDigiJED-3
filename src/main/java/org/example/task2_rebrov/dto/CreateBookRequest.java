package org.example.task2_rebrov.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class CreateBookRequest {
    @NotBlank(message = "Book title cannot be blank")
    @Size(max = 200, message = "Book title must be less than 200 characters")
    private String title;

    @NotNull(message = "Author ID cannot be null")
    @Positive(message = "Author ID must be positive")
    private Long authorId;

    @NotNull(message = "Publication year cannot be null")
    @Min(value = 1000, message = "Publication year must be realistic")
    private Integer year;
}
