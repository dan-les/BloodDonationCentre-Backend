package com.rootuss.BloodDonationCentre.security.services;

import com.rootuss.BloodDonationCentre.exception.TokenRefreshException;
import com.rootuss.BloodDonationCentre.security.jwt.model.RefreshToken;
import com.rootuss.BloodDonationCentre.security.jwt.repository.RefreshTokenRepository;
import com.rootuss.BloodDonationCentre.users.model.User;
import com.rootuss.BloodDonationCentre.users.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RefreshTokenServiceImpl implements RefreshTokenService {
    private final RefreshTokenRepository refreshTokenRepository;
    private final UserRepository userRepository;
    @Value("${app.jwtTokenRefreshExpirationMs}")
    private Long refreshTokenDurationMs;

    public Optional<RefreshToken> findByToken(String token) {
        return refreshTokenRepository.findByToken(token);
    }

    public RefreshToken createRefreshToken(User user) {
        Instant expiryDate = Instant.now().plusMillis(refreshTokenDurationMs);
        String token = UUID.randomUUID().toString();

        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setUser(user);
        refreshToken.setExpiryDate(expiryDate);
        refreshToken.setToken(token);
        refreshToken = refreshTokenRepository.save(refreshToken);
        return refreshToken;
    }

    public RefreshToken verifyExpiration(RefreshToken token) {
        if (token.getExpiryDate().compareTo(Instant.now()) < 0) {
            refreshTokenRepository.delete(token);
            throw new TokenRefreshException(token.getToken(), "Refresh token was expired. Please make a new signin request");
        }

        return token;
    }

    @Transactional
    public int deleteByUserId(Long userId) {
        return refreshTokenRepository.deleteByUser(userRepository.findById(userId).get());
    }
}
