package com.example.service;

import com.example.dto.EmailHistoryDTO;
import com.example.entity.EmailHistoryEntity;
import com.example.repository.EmailHistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.LinkedList;
import java.util.List;

@Service
public class EmailHistoryService {
    @Autowired
    private EmailHistoryRepository emailHistoryRepository;

    public List<EmailHistoryDTO> getByEmail(String email) {
        List<EmailHistoryDTO> responseDTOList = new LinkedList<>();
        emailHistoryRepository.findByEmail(email).forEach(entity -> {
            responseDTOList.add(entityToDTO(entity));
        });
        return responseDTOList;
    }

    public List<EmailHistoryDTO> getByGivenDates(String date) {
        List<EmailHistoryDTO> responseDTOList = new LinkedList<>();
        LocalDateTime time = LocalDateTime.of(LocalDate.parse(date), LocalTime.MIN);
        emailHistoryRepository.findByCreatedDate(time).forEach(entity -> {
            responseDTOList.add(entityToDTO(entity));
        });
        return responseDTOList;
    }

    public EmailHistoryDTO entityToDTO(EmailHistoryEntity entity) {
        EmailHistoryDTO dto = new EmailHistoryDTO();
        dto.setId(entity.getId());
        dto.setEmail(entity.getEmail());
        dto.setMessage(entity.getMessage());
        dto.setCreatedDate(entity.getCreatedDate());
        return dto;
    }

    public Page<EmailHistoryDTO> paging(int page, int size) {
        Sort sort = Sort.by(Sort.Direction.DESC, "createdDate");
        Pageable pageable = PageRequest.of(page - 1, size, sort);
        Page<EmailHistoryEntity> entityPage = emailHistoryRepository.findAll(pageable);
        Long totalCount = entityPage.getTotalElements();

        List<EmailHistoryDTO> responseDTOList = new LinkedList<>();
        entityPage.getContent().forEach(entity -> {
            responseDTOList.add(entityToDTO(entity));
        });
        return new PageImpl<EmailHistoryDTO>(responseDTOList, pageable, totalCount);
    }
}
