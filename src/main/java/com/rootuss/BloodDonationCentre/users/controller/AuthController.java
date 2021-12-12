package com.rootuss.BloodDonationCentre.users.controller;


import com.rootuss.BloodDonationCentre.exception.Error;
import com.rootuss.BloodDonationCentre.exception.TokenRefreshException;
import com.rootuss.BloodDonationCentre.security.jwt.JwtUtils;
import com.rootuss.BloodDonationCentre.security.jwt.model.RefreshToken;
import com.rootuss.BloodDonationCentre.security.jwt.model.request.LogOutRequestDto;
import com.rootuss.BloodDonationCentre.security.jwt.model.request.TokenRefreshRequestDto;
import com.rootuss.BloodDonationCentre.security.jwt.model.response.JwtSignInResponseDto;
import com.rootuss.BloodDonationCentre.security.jwt.model.response.TokenRefreshResponse;
import com.rootuss.BloodDonationCentre.security.services.RefreshTokenService;
import com.rootuss.BloodDonationCentre.users.model.LoginRequestDto;
import com.rootuss.BloodDonationCentre.users.model.SignupRequestDto;
import com.rootuss.BloodDonationCentre.users.service.AuthService;
import com.rootuss.BloodDonationCentre.utill.MessageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private static final String USER_SUCCESSFULLY_REGISTER = "User successfully register!";
    private static final String LOG_OUT_SUCCESSFUL = "Log out successful!";
    private final JwtUtils jwtUtils;
    private final AuthService authService;
    private final RefreshTokenService refreshTokenService;
    private final AuthenticationManager authenticationManager;


    @PostMapping("/signin")
    public ResponseEntity<?> signInUser(@Valid @RequestBody LoginRequestDto loginRequest) {
        Authentication authentication = retrieveAuthentication(loginRequest);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        JwtSignInResponseDto jwtSignInResponseDto = authService.signInUser(loginRequest, authentication);
        return ResponseEntity.ok(jwtSignInResponseDto);
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logoutUser(@Valid @RequestBody LogOutRequestDto logOutRequest) {
        refreshTokenService.deleteByUserId(logOutRequest.getUserId());
        return ResponseEntity.ok(new MessageResponse(LOG_OUT_SUCCESSFUL));
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<?> refreshtoken(@Valid @RequestBody TokenRefreshRequestDto request) {
        String requestRefreshToken = request.getRefreshToken();
        return refreshTokenService.findByToken(requestRefreshToken)
                .map(refreshTokenService::verifyExpiration)
                .map(RefreshToken::getUser)
                .map(user -> {
                    String token = jwtUtils.generateTokenFromUsername(user.getUserDetails().getUsername());
                    return ResponseEntity.ok(new TokenRefreshResponse(token, requestRefreshToken));
                })
                .orElseThrow(() -> new TokenRefreshException(requestRefreshToken,
                        Error.REFRESH_TOKEN_IS_NOT_IN_DATABASE.getMessage()));
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequestDto signUpRequest) {
        authService.registerUser(signUpRequest);
        return ResponseEntity.ok(new MessageResponse(USER_SUCCESSFULLY_REGISTER));
    }

    private Authentication retrieveAuthentication(LoginRequestDto loginRequest) {
        return authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
    }
}