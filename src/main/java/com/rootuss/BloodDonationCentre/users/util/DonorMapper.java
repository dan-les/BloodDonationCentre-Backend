package com.rootuss.BloodDonationCentre.users.util;

import com.rootuss.BloodDonationCentre.blood.utill.BloodMapper;
import com.rootuss.BloodDonationCentre.users.model.DonorRequestDto;
import com.rootuss.BloodDonationCentre.users.model.DonorResponseDto;
import com.rootuss.BloodDonationCentre.users.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class DonorMapper {
    @Autowired
    private BloodMapper bloodMapper;

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
        user.setBlood(bloodMapper.retrieveBloodGroupFromBloodName(donorRequestDto.getBloodGroupWithRh()));
        user.setEmail(donorRequestDto.getEmail());
        user.setUsername(donorRequestDto.getUsername());
        user.setGender(donorRequestDto.getGender());
        return user;
    }
}

