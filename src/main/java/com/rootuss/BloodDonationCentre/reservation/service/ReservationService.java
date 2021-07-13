package com.rootuss.BloodDonationCentre.reservation.service;

import com.rootuss.BloodDonationCentre.donation.model.DonationResponseDto;
import com.rootuss.BloodDonationCentre.reservation.model.HoursResponseDto;

import java.time.LocalDate;
import java.util.List;

public interface ReservationService {
    List<HoursResponseDto> getHoursWithAvailability(LocalDate date);
}
