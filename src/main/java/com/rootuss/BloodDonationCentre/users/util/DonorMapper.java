package com.rootuss.BloodDonationCentre.users.util;

import com.rootuss.BloodDonationCentre.blood.model.Blood;
import com.rootuss.BloodDonationCentre.blood.model.EBlood;
import com.rootuss.BloodDonationCentre.blood.utill.BloodMapper;
import com.rootuss.BloodDonationCentre.users.account.UserDetailsImpl;
import com.rootuss.BloodDonationCentre.users.model.DonorRequestDto;
import com.rootuss.BloodDonationCentre.users.model.DonorResponseDto;
import com.rootuss.BloodDonationCentre.users.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import static java.util.Optional.ofNullable;


@Component
@RequiredArgsConstructor
public class DonorMapper {
    private final BloodMapper bloodMapper;

    public DonorResponseDto mapToDonorResponseDto(User user) {
        String bloodGroupWithRh = ofNullable(user.getBlood())
                .map(Blood::getName)
                .map(EBlood::getStringName)
                .orElse(null);

        return DonorResponseDto.builder()
                .id(user.getId())
                .username(ofNullable(user.getUserDetails().getUsername()).orElse(null))
                .email(ofNullable(user.getEmail()).orElse(null))
                .firstName(ofNullable(user.getFirstName()).orElse(null))
                .lastName(ofNullable(user.getLastName()).orElse(null))
                .pesel(ofNullable(user.getPesel()).orElse(null))
                .bloodGroupWithRh(bloodGroupWithRh)
                .gender(ofNullable(user.getGender()).orElse(null))
                .build();
    }

    public User mapDonorRequestDtoToDonor(DonorRequestDto donorRequestDto) {
        User user = new User();
        UserDetailsImpl userDetails = new UserDetailsImpl();

        user.setFirstName(donorRequestDto.getFirstName());
        user.setLastName(donorRequestDto.getLastName());
        user.setPesel(donorRequestDto.getPesel());
        user.setBlood(bloodMapper.retrieveBloodGroupFromBloodName(donorRequestDto.getBloodGroupWithRh()));
        user.setEmail(donorRequestDto.getEmail());
        user.setGender(donorRequestDto.getGender());
        userDetails.setUsername(donorRequestDto.getUsername());
        user.setUserDetails(userDetails);
        return user;
    }
}

