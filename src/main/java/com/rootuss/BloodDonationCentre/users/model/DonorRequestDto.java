package com.rootuss.BloodDonationCentre.users.model;

import lombok.Getter;

import javax.validation.constraints.NotNull;

@Getter
public class DonorRequestDto {
    @NotNull(message = "Login can not be blank")
    private String username;
    @NotNull(message = "Email can not be blank")
    private String email;
    @NotNull(message = "First name can not be blank")
    private String firstName;
    @NotNull(message = "Last name can not be blank")
    private String lastName;
    @NotNull(message = "Pesel can not be blank")
    private String pesel;
    private String bloodGroupWithRh;
    private String gender;
}

