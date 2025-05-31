package org.example.task2_rebrov.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CreateAuthorRequest {
    @NotBlank(message = "Author name cannot be blank")
    @Size(max = 100, message = "Author name must be less than 100 characters")
    private String name;

    @Size(max = 50, message = "Country name must be less than 50 characters")
    private String country;
}
