package com.example.controller;

import com.example.dto.EmailHistoryDTO;
import com.example.service.EmailHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/email-history")
public class EmailHistoryController {
    @Autowired
    private EmailHistoryService emailHistoryService;
    @GetMapping("/by-email")
    public ResponseEntity<List<EmailHistoryDTO>> getByEmail(@RequestParam("email") String email) {
        return ResponseEntity.ok(emailHistoryService.getByEmail(email));
    }
    @GetMapping("/by-given-dates")
    public ResponseEntity<List<EmailHistoryDTO>> getByGivenDates(@RequestParam("date") String date) {
        return ResponseEntity.ok(emailHistoryService.getByGivenDates(date));
    }
    @GetMapping("/paging")
    public ResponseEntity<Page<EmailHistoryDTO>> paging(@RequestParam(value = "page",defaultValue = "1")int page,
                                                        @RequestParam(value = "size",defaultValue = "30")int size){
        return ResponseEntity.ok(emailHistoryService.paging(page,size));
    }
    
}
