package com.rootuss.BloodDonationCentre.donation.utill;

import com.rootuss.BloodDonationCentre.blood.model.Blood;
import com.rootuss.BloodDonationCentre.blood.model.EBlood;
import com.rootuss.BloodDonationCentre.donation.model.Donation;
import com.rootuss.BloodDonationCentre.donation.model.DonationRequestDto;
import com.rootuss.BloodDonationCentre.donation.model.DonationResponseDto;
import com.rootuss.BloodDonationCentre.donation.model.EDonationType;
import com.rootuss.BloodDonationCentre.exception.BloodDonationCentreException;
import com.rootuss.BloodDonationCentre.exception.Error;
import com.rootuss.BloodDonationCentre.recipent.model.Recipient;
import com.rootuss.BloodDonationCentre.recipent.repository.RecipientRepository;
import com.rootuss.BloodDonationCentre.users.model.User;
import com.rootuss.BloodDonationCentre.users.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import static java.util.Optional.ofNullable;


@Component
@RequiredArgsConstructor
public class DonationMapper {
    public static final String PLASMA = "plasma";
    private final UserRepository userRepository;
    private final RecipientRepository recipientRepository;

    public DonationResponseDto mapToDonationResponseDto(Donation donation) {
        String recipientName = ofNullable(donation.getRecipient())
                .map(Recipient::getName).orElse(null);
        Long recipientId = ofNullable(donation.getRecipient())
                .map(Recipient::getId).orElse(null);
        String bloodGroupWithRh = ofNullable(donation.getUser().getBlood())
                .map(Blood::getName)
                .map(EBlood::getStringName).orElse(null);
        return DonationResponseDto.builder()
                .id(donation.getId())
                .date(donation.getDate())
                .donorId(donation.getUser().getId())
                .donorFirstName(donation.getUser().getFirstName())
                .donorLastName(donation.getUser().getLastName())
                .amount(donation.getAmount())
                .donationType(donation.getDonationType().getName())
                .bloodGroupWithRh(bloodGroupWithRh)
                .isReleased(donation.getIsReleased())
                .recipientId(recipientId)
                .recipientName(recipientName)
                .build();
    }

    public Donation mapDonationRequestDtoToDonation(DonationRequestDto donationRequestDto) {
        Donation donation = new Donation();
        donation.setAmount(donationRequestDto.getAmount());
        donation.setDate(donationRequestDto.getDate());
        donation.setIsReleased(donationRequestDto.getIsReleased());
        donation.setDonationType(retrieveEDonationType(donationRequestDto.getDonationType()));
        donation.setRecipient(retrieveRecipient(donationRequestDto));
        donation.setUser(retrieveUser(donationRequestDto));
        return donation;
    }

    private User retrieveUser(DonationRequestDto donationRequestDto) {
        return userRepository.findById(donationRequestDto.getDonorId())
                .orElseThrow(() -> new BloodDonationCentreException(Error.USER_DONOR_NOT_FOUND));
    }

    private Recipient retrieveRecipient(DonationRequestDto donationRequestDto) {
        return donationRequestDto.getRecipientId() == null ?
                null : recipientRepository.findById(donationRequestDto.getRecipientId()).orElse(null);
    }

    public EDonationType retrieveEDonationType(String donationType) {
        return donationType.equals(PLASMA) ? EDonationType.PLASMA : EDonationType.BLOOD;
    }
}
