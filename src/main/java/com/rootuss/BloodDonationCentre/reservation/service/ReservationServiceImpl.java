package com.rootuss.BloodDonationCentre.reservation.service;

import com.rootuss.BloodDonationCentre.exception.BloodDonationCentreException;
import com.rootuss.BloodDonationCentre.exception.Error;
import com.rootuss.BloodDonationCentre.reservation.model.HoursResponseDto;
import com.rootuss.BloodDonationCentre.reservation.model.Reservation;
import com.rootuss.BloodDonationCentre.reservation.model.ReservationRequestDto;
import com.rootuss.BloodDonationCentre.reservation.model.ReservationResponseDto;
import com.rootuss.BloodDonationCentre.reservation.repository.ReservationRepository;
import com.rootuss.BloodDonationCentre.reservation.utill.ReservationMapper;
import com.rootuss.BloodDonationCentre.users.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReservationServiceImpl implements ReservationService {
    private final ReservationRepository reservationRepository;
    private final UserRepository userRepository;
    private final ReservationMapper reservationMapper;

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

    @Override
    public ReservationResponseDto addReservation(ReservationRequestDto reservationRequestDto) {
        Reservation reservation = reservationMapper.mapReservationRequestDtoToReservation(reservationRequestDto);
        reservation = reservationRepository.save(reservation);
        return reservationMapper.mapToReservationResponseDto(reservation);
    }

    @Override
    public List<ReservationResponseDto> getAllReservations() {
        return reservationRepository.findAll().stream()
                .map(reservationMapper::mapToReservationResponseDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<ReservationResponseDto> getAllReservationsByDate(LocalDate date) {
        return reservationRepository.findAllByDate(date).stream()
                .map(reservationMapper::mapToReservationResponseDto)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteById(Long id) {
        reservationRepository.deleteById(id);
    }

    @Override
    public ReservationResponseDto getReservationById(Long id) {
        Reservation reservation = reservationRepository.findById(id).orElseThrow(
                () -> new BloodDonationCentreException(Error.RESERVATION_NOT_FOUND));
        return reservationMapper.mapToReservationResponseDto(reservation);
    }

    @Override
    public List<ReservationResponseDto> getAllReservationsByDonorId(Long donorId) {
        return reservationRepository.findAllByDonorId(donorId).stream()
                .map(reservationMapper::mapToReservationResponseDto)
                .collect(Collectors.toList());
    }
}
