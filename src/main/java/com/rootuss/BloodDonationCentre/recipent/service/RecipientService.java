package com.rootuss.BloodDonationCentre.recipent.service;

import com.rootuss.BloodDonationCentre.recipent.controller.RecipientRequestDto;
import com.rootuss.BloodDonationCentre.recipent.model.Recipient;

import java.util.List;

public interface RecipientService {
    List<Recipient> getAllRecipients();

    Recipient addRecipient(RecipientRequestDto recipientRequestDto);
}
