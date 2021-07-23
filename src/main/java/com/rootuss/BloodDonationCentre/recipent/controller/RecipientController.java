package com.rootuss.BloodDonationCentre.recipent.controller;

import com.rootuss.BloodDonationCentre.recipent.model.Recipient;
import com.rootuss.BloodDonationCentre.recipent.service.RecipientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController

@RequestMapping("/api/recipient")
public class RecipientController {
    @Autowired
    private RecipientService recipientService;

    @GetMapping("/list")
    @PreAuthorize("hasRole('STAFF')")
    public List<Recipient> getRecipients() {
        return recipientService.getAllRecipients();
    }

    @PreAuthorize("hasRole('STAFF')")
    @PostMapping
    public Recipient addRecipient(@RequestBody RecipientRequestDto recipientRequestDto) {
        return recipientService.addRecipient(recipientRequestDto);
    }
}
