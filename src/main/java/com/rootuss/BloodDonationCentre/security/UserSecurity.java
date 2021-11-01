package com.rootuss.BloodDonationCentre.security;

import com.rootuss.BloodDonationCentre.reservation.model.Reservation;
import com.rootuss.BloodDonationCentre.reservation.repository.ReservationRepository;
import com.rootuss.BloodDonationCentre.users.account.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component("userSecurity")
@RequiredArgsConstructor
public class UserSecurity {
    private final ReservationRepository reservationRepository;

    public boolean isLoggedUserAbleToRetrieveDataByPassedDonorId(Authentication authentication, Long userId) {
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        return userId != null && userId.equals(userDetails.getId());
    }

    public boolean isLoggedUserAbleToDeleteChosenReservation(Authentication authentication, Long reservationId) {
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<Long> userReservationsIds = reservationRepository.findAllByDonorId(userDetails.getId()).stream()
                .map(Reservation::getId)
                .collect(Collectors.toList());
        return userReservationsIds.contains(reservationId);
    }
}
