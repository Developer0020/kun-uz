package com.example.controller;

import com.example.dto.EmailHistoryDTO;
import com.example.service.EmailHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/email-history")
public class EmailHistoryController {
    @Autowired
    private EmailHistoryService emailHistoryService;

    @PostMapping()
    public ResponseEntity<?> create() {
        return null;
    }

    ;

    @GetMapping("/by-email")
    public ResponseEntity<List<EmailHistoryDTO>> getByEmail(@RequestParam("email") String email) {
        return ResponseEntity.ok(emailHistoryService.getByEmail(email));
    }

    ///Get EmailHistory  by given date
    //            (id, email,message,created_date)

    @GetMapping("/by-given-dates")
    public ResponseEntity<List<EmailHistoryDTO>> getByGivenDates(@RequestParam("date") String date) {
        return ResponseEntity.ok(emailHistoryService.getByGivenDates(date));
    }
}
