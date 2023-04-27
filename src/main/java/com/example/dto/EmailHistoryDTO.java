package com.example.dto;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
@Getter
@Setter
public class EmailHistoryDTO {

    private Integer id;

    private String message;

    private String email;

    private LocalDateTime createdDate;
}
