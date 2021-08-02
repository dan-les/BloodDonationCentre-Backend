package com.rootuss.BloodDonationCentre.security.jwt.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class TokenRefreshRequest {
  @NotBlank
  private String refreshToken;
}
