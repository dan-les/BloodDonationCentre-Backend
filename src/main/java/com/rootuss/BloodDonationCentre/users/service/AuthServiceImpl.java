package com.rootuss.BloodDonationCentre.users.service;

import com.rootuss.BloodDonationCentre.exception.BloodDonationCentreException;
import com.rootuss.BloodDonationCentre.exception.Error;
import com.rootuss.BloodDonationCentre.security.jwt.JwtUtils;
import com.rootuss.BloodDonationCentre.security.jwt.model.RefreshToken;
import com.rootuss.BloodDonationCentre.security.jwt.model.response.JwtSignInResponseDto;
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
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private static final String ADMIN = "admin";
    private static final String STAFF = "staff";
    private final PasswordEncoder encoder;
    private final JwtUtils jwtUtils;
    private final RefreshTokenService refreshTokenService;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserDetailsRepository userDetailsRepository;

    @Override
    public JwtSignInResponseDto signInUser(LoginRequestDto loginRequest, Authentication authentication) {
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        User user = retrieveUser(userDetails);
        String jwtToken = jwtUtils.generateJwtToken(authentication);
        RefreshToken refreshToken = refreshTokenService.createRefreshToken(user);
        List<String> roles = retrieveRoles(userDetails.getAuthorities());

        return JwtSignInResponseDto.builder()
                .token(jwtToken)
                .refreshToken(refreshToken.getToken())
                .id(user.getId())
                .username(user.getUserDetails().getUsername())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .roles(roles)
                .build();
    }

    @Override
    public void registerUser(SignupRequestDto signUpRequest) {
        if (userDetailsRepository.existsByUsername(signUpRequest.getUsername())) {
            throw new BloodDonationCentreException(Error.USERNAME_IS_ALREADY_TAKEN);
        }

        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            throw new BloodDonationCentreException(Error.EMAIL_IS_ALREADY_IN_USE);
        }

        User user = retrieveUser(signUpRequest);
        Set<String> strRoles = signUpRequest.getRole();
        Set<Role> roles = new HashSet<>();

        if (strRoles == null) {
            Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                    .orElseThrow(() -> new BloodDonationCentreException(Error.ROLE_NOT_FOUND));
            roles.add(userRole);
        } else {
            strRoles.forEach(role -> {
                switch (role) {
                    case ADMIN:
                        Role adminRole = retrieveRole(ERole.ROLE_ADMIN);
                        roles.add(adminRole);

                        break;
                    case STAFF:
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
    }

    private User retrieveUser(SignupRequestDto signUpRequest) {
        User user = new User();
        user.setEmail(signUpRequest.getEmail());
        user.setFirstName(signUpRequest.getFirstName());
        user.setLastName(signUpRequest.getLastName());
        user.setUserDetails(retrieveUserDetails(signUpRequest));
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
        return userRepository.findById(userDetails.getId())
                .orElseThrow(() -> new BloodDonationCentreException(Error.USER_DONOR_NOT_FOUND));
    }

    private List<String> retrieveRoles(Collection<? extends GrantedAuthority> grantedAuthorities) {
        return grantedAuthorities.stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());
    }
}
