package com.rootuss.BloodDonationCentre.recipent.service;

import com.rootuss.BloodDonationCentre.recipent.model.Recipient;
import com.rootuss.BloodDonationCentre.recipent.model.RecipientRequestDto;

import java.util.List;

public interface RecipientService {

    List<Recipient> getAllRecipients();

    Recipient addRecipient(RecipientRequestDto recipientRequestDto);
}
