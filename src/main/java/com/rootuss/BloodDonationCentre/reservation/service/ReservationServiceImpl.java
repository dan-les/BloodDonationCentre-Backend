package com.rootuss.BloodDonationCentre.reservation.service;

import com.rootuss.BloodDonationCentre.reservation.model.HoursResponseDto;
import com.rootuss.BloodDonationCentre.reservation.repository.ReservationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReservationServiceImpl implements ReservationService {
    private final ReservationRepository reservationRepository;
    //więcej niż 2 rezerwacje na jeden termin to dajemy disabled
    private final int maxQuantityOfReservationsInTheSameTime = 2;
    private final int firstEntry = 8;
    private final int lastEntry = 19;

    @Override
    public List<HoursResponseDto> getHoursWithAvailability(LocalDate date) {
        List<HoursResponseDto> list = new ArrayList<>();
        for (int i = firstEntry; i <= lastEntry; i++) {
            if (reservationRepository.getAllByTimeAndDate(LocalTime.of(i, 0), date).size() >= maxQuantityOfReservationsInTheSameTime) {
                list.add(HoursResponseDto.builder().hour(LocalTime.of(i, 0).toString()).disabled(true).build());
            } else {
                list.add(HoursResponseDto.builder().hour(LocalTime.of(i, 0).toString()).disabled(false).build());
            }
        }
        return list;
    }
}
