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

//        return DonorResponseDto.builder()
//                .id(user.getId())
//                .username(user.getUsername() == null ? "" : user.getUsername())
//                .email(user.getEmail() == null ? "" : user.getEmail())
//                .firstName(user.getFirstName() == null ? "" : user.getFirstName())
//                .lastName(user.getLastName() == null ? "" : user.getLastName())
//                .pesel(user.getPesel() == null ? "" : user.getPesel())
//                .bloodGroupWithRh(user.getBlood() == null ? "" : user.getBlood().getName().getStringName())
//                .gender(user.getGender() == null ? "" : user.getGender())
//                .build();
        return null;
    }

    public Donation mapDonationRequestDtoToDonation(DonationRequestDto donationRequestDto) {
        Donation donation = new Donation();
        donation.setAmount(donationRequestDto.getAmount());
        donation.setDate(donationRequestDto.getDate());
        donation.setIsReleased(donationRequestDto.getIsReleased());
        donation.setDonationType(donationRequestDto.getDonationType().equals("plasma") ?
                EDonationType.PLASMA : EDonationType.BLOOD);
        donation.setRecipient(recipientRepository.findById(donationRequestDto.getRecipientId()).orElse(null));
        donation.setUser(userRepository.findById(donationRequestDto.getDonorId())
                .orElseThrow(() -> new BloodDonationCentreException(Error.USER_DONOR_NOT_FOUND)));

        return donation;
    }


}

