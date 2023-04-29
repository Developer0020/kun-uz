package com.example.dto.articleType;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ArticleTypeRequestDTO {
    private Integer id;
    @NotNull(message = "name is null")
    private String name;
}
