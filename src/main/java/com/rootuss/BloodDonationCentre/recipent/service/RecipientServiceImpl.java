package com.rootuss.BloodDonationCentre.recipent.service;

import com.rootuss.BloodDonationCentre.recipent.controller.RecipientRequestDto;
import com.rootuss.BloodDonationCentre.recipent.model.Recipient;
import com.rootuss.BloodDonationCentre.recipent.repository.RecipientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service

public class RecipientServiceImpl implements RecipientService {
    @Autowired
    private RecipientRepository recipientRepository;

    @Override
    public List<Recipient> getAllRecipients() {
        return recipientRepository.findAll();
    }

    @Override
    public Recipient addRecipient(RecipientRequestDto recipientRequestDto) {
        Recipient recipient = new Recipient();
        recipient.setName(recipientRequestDto.getName());
        recipient = recipientRepository.save(recipient);
        return recipient;
    }
}
