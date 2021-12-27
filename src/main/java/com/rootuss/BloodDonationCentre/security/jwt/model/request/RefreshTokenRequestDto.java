package com.rootuss.BloodDonationCentre.security.jwt.model.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class RefreshTokenRequestDto {
  @NotNull(message = "RefreshToken can not be blank")
  private String refreshToken;
}
