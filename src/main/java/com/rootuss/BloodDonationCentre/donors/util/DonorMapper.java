package com.rootuss.BloodDonationCentre.donors.util;

import com.rootuss.BloodDonationCentre.donors.model.Donor;
import com.rootuss.BloodDonationCentre.donors.model.DonorResponseDto;
import org.springframework.stereotype.Component;

@Component
public class DonorMapper {
    public DonorResponseDto mapToDonorResponseDto(Donor donor) {
        return DonorResponseDto.builder()
                .id(donor.getId())
                .firstName(donor.getFirstName())
                .lastName(donor.getLastName())
                .pesel(donor.getPesel())
                .bloodGroupWithRh(donor.getBlood().getName().getName())
                .build();
    }
}

