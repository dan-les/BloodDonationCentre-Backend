package com.rootuss.BloodDonationCentre.donation.utill;

import com.rootuss.BloodDonationCentre.donation.model.Donation;
import com.rootuss.BloodDonationCentre.donation.model.DonationRequestDto;
import com.rootuss.BloodDonationCentre.donation.model.DonationResponseDto;
import com.rootuss.BloodDonationCentre.donation.model.EDonationType;
import com.rootuss.BloodDonationCentre.exception.BloodDonationCentreException;
import com.rootuss.BloodDonationCentre.exception.Error;
import com.rootuss.BloodDonationCentre.recipent.repository.RecipientRepository;
import com.rootuss.BloodDonationCentre.users.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;


@Component
@AllArgsConstructor
public class DonationMapper {
    private final UserRepository userRepository;
    private final RecipientRepository recipientRepository;

    public DonationResponseDto mapToDonationResponseDto(Donation donation) {
        return DonationResponseDto.builder()
                .id(donation.getId())
                .date(donation.getDate())
                .donorId(donation.getUser().getId())
                .donorFirstName(donation.getUser().getFirstName())
                .donorLastName(donation.getUser().getLastName())
                .amount(donation.getAmount())
                .donationType(donation.getDonationType().getName())
                .bloodGroupWithRh(donation.getUser().getBlood().getName().getStringName())
                .isReleased(donation.getIsReleased())
                .recipientId(donation.getRecipient() == null ? null : donation.getRecipient().getId())
                .recipientName(donation.getRecipient() == null ? null : donation.getRecipient().getName())
                .build();
    }

    public Donation mapDonationRequestDtoToDonation(DonationRequestDto donationRequestDto) {
        Donation donation = new Donation();
        donation.setAmount(donationRequestDto.getAmount());
        donation.setDate(donationRequestDto.getDate());
        donation.setIsReleased(donationRequestDto.getIsReleased());
        donation.setDonationType(retrieveEDonationType(donationRequestDto.getDonationType()));
        donation.setRecipient(donationRequestDto.getRecipientId() == null ?
                null : recipientRepository.findById(donationRequestDto.getRecipientId()).orElse(null));
        donation.setUser(userRepository.findById(donationRequestDto.getDonorId())
                .orElseThrow(() -> new BloodDonationCentreException(Error.USER_DONOR_NOT_FOUND)));

        return donation;
    }

    public EDonationType retrieveEDonationType(String donationType) {
        return donationType.equals("plasma") ? EDonationType.PLASMA : EDonationType.BLOOD;
    }
}
