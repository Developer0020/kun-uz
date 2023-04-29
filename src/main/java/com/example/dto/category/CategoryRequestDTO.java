package com.example.dto.category;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CategoryRequestDTO {
    private Integer id;
    @NotNull(message = "name is null")
    private String name;
}
