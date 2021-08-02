package com.rootuss.BloodDonationCentre.security.jwt.response;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class TokenRefreshResponse {
    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)

    private final String tokenType = "Bearer";
    private String accessToken;
    private String refreshToken;
}
