package com.rootuss.BloodDonationCentre.users.controller;


import com.rootuss.BloodDonationCentre.exception.BloodDonationCentreException;
import com.rootuss.BloodDonationCentre.exception.Error;
import com.rootuss.BloodDonationCentre.exception.TokenRefreshException;
import com.rootuss.BloodDonationCentre.security.jwt.JwtUtils;
import com.rootuss.BloodDonationCentre.security.jwt.model.RefreshToken;
import com.rootuss.BloodDonationCentre.security.jwt.model.request.LogOutRequest;
import com.rootuss.BloodDonationCentre.security.jwt.model.request.TokenRefreshRequest;
import com.rootuss.BloodDonationCentre.security.jwt.model.response.JwtResponse;
import com.rootuss.BloodDonationCentre.security.jwt.model.response.TokenRefreshResponse;
import com.rootuss.BloodDonationCentre.security.services.RefreshTokenService;
import com.rootuss.BloodDonationCentre.users.account.UserDetailsImpl;
import com.rootuss.BloodDonationCentre.users.account.UserDetailsRepository;
import com.rootuss.BloodDonationCentre.users.model.LoginRequestDto;
import com.rootuss.BloodDonationCentre.users.model.SignupRequestDto;
import com.rootuss.BloodDonationCentre.users.model.User;
import com.rootuss.BloodDonationCentre.users.repository.UserRepository;
import com.rootuss.BloodDonationCentre.users.roles.model.ERole;
import com.rootuss.BloodDonationCentre.users.roles.model.Role;
import com.rootuss.BloodDonationCentre.users.roles.repository.RoleRepository;
import com.rootuss.BloodDonationCentre.utill.MessageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final UserDetailsRepository userDetailsRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder encoder;
    private final JwtUtils jwtUtils;
    private final RefreshTokenService refreshTokenService;

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequestDto loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        User user = retrieveUser(userDetails);
        List<String> roles = retrieveRoles(userDetails);

        RefreshToken refreshToken = refreshTokenService.createRefreshToken(userDetails.getId());

        return ResponseEntity.ok(
                new JwtResponse(
                        jwt,
                        refreshToken.getToken(),
                        user.getId(),
                        user.getUserDetails().getUsername(),
                        user.getFirstName(),
                        user.getLastName(),
                        user.getEmail(),
                        roles
                )
        );
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logoutUser(@Valid @RequestBody LogOutRequest logOutRequest) {
        refreshTokenService.deleteByUserId(logOutRequest.getUserId());
        return ResponseEntity.ok(new MessageResponse("Log out successful!"));
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<?> refreshtoken(@Valid @RequestBody TokenRefreshRequest request) {
        String requestRefreshToken = request.getRefreshToken();

        return refreshTokenService.findByToken(requestRefreshToken)
                .map(refreshTokenService::verifyExpiration)
                .map(RefreshToken::getUser)
                .map(user -> {
                    String token = jwtUtils.generateTokenFromUsername(user.getUserDetails().getUsername());
                    return ResponseEntity.ok(new TokenRefreshResponse(token, requestRefreshToken));
                })
                .orElseThrow(() -> new TokenRefreshException(requestRefreshToken,
                        "Refresh token is not in database!"));
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequestDto signUpRequest) {
        if (userDetailsRepository.existsByUsername(signUpRequest.getUsername())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Username is already taken!"));
        }

        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Email is already in use!"));
        }

        User user = retrieveUser(signUpRequest);
        Set<String> strRoles = signUpRequest.getRole();
        Set<Role> roles = new HashSet<>();

        if (strRoles == null) {
            Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            roles.add(userRole);
        } else {
            strRoles.forEach(role -> {
                switch (role) {
                    case "admin":
                        Role adminRole = retrieveRole(ERole.ROLE_ADMIN);
                        roles.add(adminRole);

                        break;
                    case "staff":
                        Role staffRole = retrieveRole(ERole.ROLE_STAFF);
                        roles.add(staffRole);

                        break;
                    default:
                        Role userRole = retrieveRole(ERole.ROLE_USER);
                        roles.add(userRole);
                }
            });
        }

        user.getUserDetails().setRoles(roles);
        userRepository.save(user);

        return ResponseEntity.ok(new MessageResponse("User successfully register!"));
    }

    private User retrieveUser(SignupRequestDto signUpRequest) {
        User user = new User(
                null,
                signUpRequest.getEmail(),
                signUpRequest.getFirstName(),
                signUpRequest.getLastName(),
                null,
                null,
                null,
                retrieveUserDetails(signUpRequest));
        return user;
    }

    private UserDetailsImpl retrieveUserDetails(SignupRequestDto signUpRequest) {
        UserDetailsImpl userDetails = new UserDetailsImpl();
        userDetails.setAccountNonExpired(true);
        userDetails.setAccountNonLocked(true);
        userDetails.setCredentialsNonExpired(true);
        userDetails.setEnabled(true);
        userDetails.setPassword(encoder.encode(signUpRequest.getPassword()));
        userDetails.setUsername(signUpRequest.getUsername());
        return userDetails;
    }

    private Role retrieveRole(ERole roleAdmin) {
        return roleRepository.findByName(roleAdmin)
                .orElseThrow(() -> new BloodDonationCentreException(Error.ROLE_NOT_FOUND));
    }


    private User retrieveUser(UserDetailsImpl userDetails) {
        return Optional.ofNullable(userRepository.findById(userDetails.getId()))
                .filter(Optional::isPresent)
                .stream()
                .findAny()
                .map(Optional::get)
                .orElseThrow(() -> new BloodDonationCentreException(Error.USER_DONOR_NOT_FOUND));
    }

    private List<String> retrieveRoles(UserDetailsImpl userDetails) {
        return userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());
    }
}