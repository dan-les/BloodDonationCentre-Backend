package com.rootuss.BloodDonationCentre.donors.model;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class DonorResponseDto {
    private Long id;
    private String firstName;
    private String lastName;
    private String pesel;
    private String bloodGroupWithRh;
}
