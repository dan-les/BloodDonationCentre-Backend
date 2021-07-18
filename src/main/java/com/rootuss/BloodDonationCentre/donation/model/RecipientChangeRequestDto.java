package com.rootuss.BloodDonationCentre.donation.model;

import lombok.Getter;

import javax.validation.constraints.NotNull;

@Getter
public class RecipientChangeRequestDto {
    @NotNull(message = "Donation Id can not be blank")
    private Long id;
    @NotNull(message = "isReleased can not be blank")
    private Boolean isReleased;
    @NotNull(message = "Recipient Id can not be blank")
    private Long recipientId;

}
