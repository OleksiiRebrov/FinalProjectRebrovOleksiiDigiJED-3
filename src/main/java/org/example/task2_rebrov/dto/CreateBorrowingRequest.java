package org.example.task2_rebrov.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CreateBorrowingRequest {
    @NotNull(message = "Book ID cannot be null")
    @Positive(message = "Book ID must be positive")
    private Long bookId;

    @NotBlank(message = "Borrower name cannot be blank")
    @Size(max = 100, message = "Borrower name must be less than 100 characters")
    private String borrowerName;
}
