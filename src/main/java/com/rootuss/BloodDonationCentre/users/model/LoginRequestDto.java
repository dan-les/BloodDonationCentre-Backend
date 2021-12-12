package com.rootuss.BloodDonationCentre.users.model;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class LoginRequestDto {
    @NotNull(message = "Username can not be blank")
    private String username;
    @NotNull(message = "Password can not be blank")
    private String password;
}