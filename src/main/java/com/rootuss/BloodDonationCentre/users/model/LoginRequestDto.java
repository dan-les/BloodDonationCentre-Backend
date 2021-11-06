package com.rootuss.BloodDonationCentre.users.model;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class LoginRequestDto {
    @NotBlank
    private String username;
    @NotBlank
    private String password;
}