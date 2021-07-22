package com.rootuss.BloodDonationCentre.reservation.utill;

import com.rootuss.BloodDonationCentre.donation.model.EDonationType;
import com.rootuss.BloodDonationCentre.exception.BloodDonationCentreException;
import com.rootuss.BloodDonationCentre.exception.Error;
import com.rootuss.BloodDonationCentre.reservation.model.Reservation;
import com.rootuss.BloodDonationCentre.reservation.model.ReservationRequestDto;
import com.rootuss.BloodDonationCentre.reservation.model.ReservationResponseDto;
import com.rootuss.BloodDonationCentre.users.model.User;
import com.rootuss.BloodDonationCentre.users.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalTime;

@Component
public class ReservationMapper {
    @Autowired
    private UserRepository userRepository;

    public Reservation mapReservationRequestDtoToReservation(ReservationRequestDto reservationRequestDto) {
        Reservation reservation = new Reservation();
        User user = userRepository.findById(reservationRequestDto.getDonorId())
                .orElseThrow(() -> new BloodDonationCentreException(Error.USER_DONOR_NOT_FOUND));
        LocalDate date = LocalDate.parse(reservationRequestDto.getDate().toString());
        LocalTime time = LocalTime.parse(reservationRequestDto.getTime().toString());
        EDonationType donationType;
        if (reservationRequestDto.getDonationType().equals("plasma")) {
            donationType = EDonationType.PLASMA;
        } else {
            donationType = EDonationType.BLOOD;
        }
        reservation.setDate(date);
        reservation.setTime(time);
        reservation.setUser(user);
        reservation.setDonationType(donationType);
        return reservation;
    }

    public ReservationResponseDto mapToReservationResponseDto(Reservation reservation) {
        return ReservationResponseDto.builder()
                .id(reservation.getId())
                .date(reservation.getDate().toString())
                .time(reservation.getTime().toString())
                .donorId(reservation.getUser().getId())
                .donorFirstName(reservation.getUser().getFirstName())
                .donorLastName(reservation.getUser().getLastName())
                .pesel(reservation.getUser().getPesel())
                .donationType(reservation.getDonationType().getName())
                .build();
    }
}
