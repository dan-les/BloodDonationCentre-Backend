package com.rootuss.BloodDonationCentre.reservation.service;

import com.rootuss.BloodDonationCentre.reservation.model.HoursResponseDto;
import com.rootuss.BloodDonationCentre.reservation.model.ReservationRequestDto;
import com.rootuss.BloodDonationCentre.reservation.model.ReservationResponseDto;

import java.time.LocalDate;
import java.util.List;

public interface ReservationService {
    List<HoursResponseDto> getHoursWithAvailability(LocalDate date);

    ReservationResponseDto addReservation(ReservationRequestDto reservationRequestDto);

    List<ReservationResponseDto> getAllReservations();

    List<ReservationResponseDto> getAllReservationsByDate(LocalDate date);

    void deleteById(Long id);

    ReservationResponseDto getReservationById(Long id);

}
