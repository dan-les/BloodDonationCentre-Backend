package com.rootuss.BloodDonationCentre.users.service;

import com.rootuss.BloodDonationCentre.security.jwt.model.response.JwtSignInResponse;
import com.rootuss.BloodDonationCentre.users.model.LoginRequestDto;
import com.rootuss.BloodDonationCentre.users.model.SignupRequestDto;
import org.springframework.security.core.Authentication;

public interface AuthService {
    JwtSignInResponse signInUser(LoginRequestDto loginRequest, Authentication authentication);

    void registerUser(SignupRequestDto signUpRequest);
}
