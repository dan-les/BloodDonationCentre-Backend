package com.rootuss.BloodDonationCentre.donation.utill;

import com.rootuss.BloodDonationCentre.donation.model.Donation;
import com.rootuss.BloodDonationCentre.donation.model.DonationRequestDto;
import com.rootuss.BloodDonationCentre.users.model.DonorResponseDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;


@Component
@AllArgsConstructor
public class DonationMapper {


    public DonorResponseDto mapToDonationResponseDto(Donation donation) {

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
//        User user = new User();
//        user.setFirstName(donorRequestDto.getFirstName());
//        user.setLastName(donorRequestDto.getLastName());
//        user.setPesel(donorRequestDto.getPesel());
//        user.setBlood(getBloodGroupFromDonorRequestDto(donorRequestDto));
//        user.setEmail(donorRequestDto.getEmail());
//        user.setUsername(donorRequestDto.getUsername());
//        user.setGender(donorRequestDto.getGender());
//        return user;
        return null;
    }


}

