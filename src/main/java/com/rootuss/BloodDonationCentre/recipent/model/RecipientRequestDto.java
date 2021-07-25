package com.rootuss.BloodDonationCentre.recipent.model;

import lombok.Getter;

import javax.validation.constraints.NotNull;

@Getter
public class RecipientRequestDto {
    @NotNull(message = "Name of recipient can not be blank")
    private String name;
}
