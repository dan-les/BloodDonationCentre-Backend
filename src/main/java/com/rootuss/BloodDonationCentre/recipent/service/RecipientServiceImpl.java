package com.rootuss.BloodDonationCentre.recipent.service;

import com.rootuss.BloodDonationCentre.recipent.model.Recipient;
import com.rootuss.BloodDonationCentre.recipent.model.RecipientRequestDto;
import com.rootuss.BloodDonationCentre.recipent.repository.RecipientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RecipientServiceImpl implements RecipientService {
    private final RecipientRepository recipientRepository;

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
