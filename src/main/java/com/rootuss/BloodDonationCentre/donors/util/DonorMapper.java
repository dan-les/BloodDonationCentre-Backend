package com.rootuss.BloodDonationCentre.donors.util;

import com.rootuss.BloodDonationCentre.blood.Blood;
import com.rootuss.BloodDonationCentre.blood.BloodRepository;
import com.rootuss.BloodDonationCentre.blood.EBlood;
import com.rootuss.BloodDonationCentre.donors.model.Donor;
import com.rootuss.BloodDonationCentre.donors.model.DonorRequestDto;
import com.rootuss.BloodDonationCentre.donors.model.DonorResponseDto;
import com.rootuss.BloodDonationCentre.exception.BloodDonationCentreException;
import com.rootuss.BloodDonationCentre.exception.Error;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Arrays;


@Component
@AllArgsConstructor
public class DonorMapper {


    BloodRepository bloodRepository;

    public DonorResponseDto mapToDonorResponseDto(Donor donor) {
        return DonorResponseDto.builder()
                .id(donor.getId())
                .firstName(donor.getFirstName())
                .lastName(donor.getLastName())
                .pesel(donor.getPesel())
                .bloodGroupWithRh(donor.getBlood().getName().getName())
                .build();
    }

    public Donor mapDonorRequestDtoToDonor(DonorRequestDto donorRequestDto) {
        Donor donor = new Donor();
        donor.setFirstName(donorRequestDto.getFirstName());
        donor.setLastName(donorRequestDto.getLastName());
        donor.setPesel(donorRequestDto.getPesel());
        donor.setBlood(getBloodGroupFromDonorRequestDto(donorRequestDto));
        return donor;
    }

    private Blood getBloodGroupFromDonorRequestDto(DonorRequestDto donorRequestDto) {

        var bloodName = donorRequestDto.getBloodGroupWithRh();
        var bloodNameEnum = Arrays.stream(EBlood.values()).filter(b -> b.getName().equals(bloodName)).findFirst()
                .orElseThrow(() -> new BloodDonationCentreException(Error.BLOOD_TYPE_NOT_FOUND));


        return bloodRepository.findByName(bloodNameEnum)
                .stream()
                .findFirst()
                .orElseThrow(() -> new BloodDonationCentreException(Error.BLOOD_TYPE_NOT_FOUND));
    }
}

