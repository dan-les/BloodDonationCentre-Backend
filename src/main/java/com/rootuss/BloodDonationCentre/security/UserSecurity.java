package com.rootuss.BloodDonationCentre.security;

import com.rootuss.BloodDonationCentre.reservation.model.Reservation;
import com.rootuss.BloodDonationCentre.reservation.repository.ReservationRepository;
import com.rootuss.BloodDonationCentre.security.services.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component("userSecurity")
@RequiredArgsConstructor
public class UserSecurity {
    private final ReservationRepository reservationRepository;

    public boolean hasProperUserId(Authentication authentication, Long userId) {
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        if (userId == userDetails.getId()) {
            return true;
        } else {
            return false;
        }
    }

    public boolean hasReservationProperUserId(Authentication authentication, Long reservationId) {
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        var userReservationsIds = reservationRepository.findAllByDonorId(userDetails.getId()).stream()
                .map(Reservation::getId)
                .collect(Collectors.toList());
        if (userReservationsIds.contains(reservationId)) {
            return true;
        } else {
            return false;
        }
    }
}
