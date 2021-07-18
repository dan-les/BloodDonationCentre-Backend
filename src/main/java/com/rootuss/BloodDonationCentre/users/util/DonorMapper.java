package com.rootuss.BloodDonationCentre.users.util;

import com.rootuss.BloodDonationCentre.blood.model.Blood;
import com.rootuss.BloodDonationCentre.blood.model.EBlood;
import com.rootuss.BloodDonationCentre.blood.repository.BloodRepository;
import com.rootuss.BloodDonationCentre.exception.BloodDonationCentreException;
import com.rootuss.BloodDonationCentre.exception.Error;
import com.rootuss.BloodDonationCentre.users.model.DonorRequestDto;
import com.rootuss.BloodDonationCentre.users.model.DonorResponseDto;
import com.rootuss.BloodDonationCentre.users.model.User;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Arrays;


@Component
@AllArgsConstructor
public class DonorMapper {


    BloodRepository bloodRepository;

    public DonorResponseDto mapToDonorResponseDto(User user) {

        return DonorResponseDto.builder()
                .id(user.getId())
                .username(user.getUsername() == null ? "" : user.getUsername())
                .email(user.getEmail() == null ? "" : user.getEmail())
                .firstName(user.getFirstName() == null ? "" : user.getFirstName())
                .lastName(user.getLastName() == null ? "" : user.getLastName())
                .pesel(user.getPesel() == null ? "" : user.getPesel())
                .bloodGroupWithRh(user.getBlood() == null ? "" : user.getBlood().getName().getStringName())
                .gender(user.getGender() == null ? "" : user.getGender())
                .build();
    }

    public User mapDonorRequestDtoToDonor(DonorRequestDto donorRequestDto) {
        User user = new User();
        user.setFirstName(donorRequestDto.getFirstName());
        user.setLastName(donorRequestDto.getLastName());
        user.setPesel(donorRequestDto.getPesel());
        user.setBlood(retrieveBloodGroupFromBloodName(donorRequestDto.getBloodGroupWithRh()));
        user.setEmail(donorRequestDto.getEmail());
        user.setUsername(donorRequestDto.getUsername());
        user.setGender(donorRequestDto.getGender());
        return user;
    }

    public Blood retrieveBloodGroupFromBloodName(String bloodName) {
        var bloodNameEnum = Arrays.stream(EBlood.values()).filter(b -> b.getStringName().equals(bloodName)).findFirst()
                .orElseThrow(() -> new BloodDonationCentreException(Error.BLOOD_TYPE_NOT_FOUND));


        return bloodRepository.findByName(bloodNameEnum)
                .stream()
                .findFirst()
                .orElseThrow(() -> new BloodDonationCentreException(Error.BLOOD_TYPE_NOT_FOUND));
    }
}

