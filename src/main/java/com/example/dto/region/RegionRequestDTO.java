package com.example.dto.region;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegionRequestDTO {
    private Integer id;
    @NotNull(message = "name is null")
    private String name;
}
