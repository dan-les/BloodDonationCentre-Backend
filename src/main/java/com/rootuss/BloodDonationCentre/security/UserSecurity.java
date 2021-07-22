package com.rootuss.BloodDonationCentre.security;

import com.rootuss.BloodDonationCentre.security.services.UserDetailsImpl;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component("userSecurity")
public class UserSecurity {
    public boolean hasProperUserId(Authentication authentication, Long userId) {
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        if (userId == userDetails.getId()) {
            return true;
        } else {
            return false;
        }
    }
}
