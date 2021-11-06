package com.rootuss.BloodDonationCentre.security.services;

import com.rootuss.BloodDonationCentre.security.jwt.model.RefreshToken;
import com.rootuss.BloodDonationCentre.users.model.User;

import java.util.Optional;

public interface RefreshTokenService {
    Optional<RefreshToken> findByToken(String token);

    RefreshToken createRefreshToken(User user);

    RefreshToken verifyExpiration(RefreshToken token);

    int deleteByUserId(Long userId);
}
