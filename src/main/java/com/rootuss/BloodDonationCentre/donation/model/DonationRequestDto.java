package com.rootuss.BloodDonationCentre.donation.model;

import lombok.Getter;

import javax.validation.constraints.NotNull;

@Getter
public class DonationRequestDto {
    @NotNull(message = "Donor Id can not be blank")
    private String donorId;
    @NotNull(message = "Type of donation can not be blank")
    private String donationType;

}
