package com.rootuss.BloodDonationCentre.users.service;

import com.rootuss.BloodDonationCentre.security.jwt.model.response.JwtSignInResponseDto;
import com.rootuss.BloodDonationCentre.users.model.LoginRequestDto;
import com.rootuss.BloodDonationCentre.users.model.SignupRequestDto;
import org.springframework.security.core.Authentication;

public interface AuthService {
    JwtSignInResponseDto signInUser(LoginRequestDto loginRequest, Authentication authentication);

    void registerUser(SignupRequestDto signUpRequest);
}
