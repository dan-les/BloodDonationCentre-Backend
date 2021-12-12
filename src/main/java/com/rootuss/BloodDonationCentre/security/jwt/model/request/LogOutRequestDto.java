package com.rootuss.BloodDonationCentre.security.jwt.model.request;

import lombok.Getter;

import javax.validation.constraints.NotNull;

@Getter
public class LogOutRequestDto {
  @NotNull(message = "UserId can not be blank")
  private Long userId;
}
