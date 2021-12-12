package com.rootuss.BloodDonationCentre.recipent.controller;

import com.rootuss.BloodDonationCentre.recipent.model.Recipient;
import com.rootuss.BloodDonationCentre.recipent.model.RecipientRequestDto;
import com.rootuss.BloodDonationCentre.recipent.service.RecipientService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController

@RequestMapping("/api/recipient")
@RequiredArgsConstructor
public class RecipientController {
    private final RecipientService recipientService;

    @GetMapping
    @PreAuthorize("hasRole('STAFF')")
    public List<Recipient> getAllRecipients() {
        return recipientService.getAllRecipients();
    }

    @PreAuthorize("hasRole('STAFF')")
    @PostMapping
    public Recipient addRecipient(@Valid @RequestBody RecipientRequestDto recipientRequestDto) {
        return recipientService.addRecipient(recipientRequestDto);
    }
}
