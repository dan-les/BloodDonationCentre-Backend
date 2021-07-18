package com.rootuss.BloodDonationCentre.recipent.controller;

import com.rootuss.BloodDonationCentre.recipent.model.Recipient;
import com.rootuss.BloodDonationCentre.recipent.service.RecipientService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/recipient")
public class RecipientController {
    private final RecipientService recipientService;

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
