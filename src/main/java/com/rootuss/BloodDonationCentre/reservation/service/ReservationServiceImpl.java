package com.rootuss.BloodDonationCentre.reservation.service;

import com.rootuss.BloodDonationCentre.donation.utill.DateValidator;
import com.rootuss.BloodDonationCentre.exception.BloodDonationCentreException;
import com.rootuss.BloodDonationCentre.exception.Error;
import com.rootuss.BloodDonationCentre.reservation.model.AvailableHoursForReservationResponseDto;
import com.rootuss.BloodDonationCentre.reservation.model.Reservation;
import com.rootuss.BloodDonationCentre.reservation.model.ReservationRequestDto;
import com.rootuss.BloodDonationCentre.reservation.model.ReservationResponseDto;
import com.rootuss.BloodDonationCentre.reservation.repository.ReservationRepository;
import com.rootuss.BloodDonationCentre.reservation.utill.ReservationMapper;
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
    private final ReservationMapper reservationMapper;
    private final DateValidator dateValidator;

    private final int maxQuantityOfReservationsInTheSameTimeSlot = 2;
    private final int firstEntry = 8;
    private final int lastEntry = 19;

    @Override
    public List<AvailableHoursForReservationResponseDto> getHoursWithAvailability(LocalDate date) {
        List<AvailableHoursForReservationResponseDto> list = new ArrayList<>();
        for (int hour = firstEntry; hour <= lastEntry; hour++) {
            if (calculateDisability(date, hour)) {
                list.add(retrieveAvailableHoursForReservationResponseDto(hour, true));
            } else {
                list.add(retrieveAvailableHoursForReservationResponseDto(hour, false));
            }
        }
        return list;
    }

    private boolean calculateDisability(LocalDate date, int i) {
        return reservationRepository.getAllByTimeAndDate(
                LocalTime.of(i, 0), date).size() >= maxQuantityOfReservationsInTheSameTimeSlot;
    }

    private AvailableHoursForReservationResponseDto retrieveAvailableHoursForReservationResponseDto(int hour, boolean isDisabled) {
        return AvailableHoursForReservationResponseDto.builder()
                .hour(LocalTime.of(hour, 0).toString())
                .disabled(isDisabled)
                .build();
    }

    @Override
    public ReservationResponseDto addReservation(ReservationRequestDto reservationRequestDto) {
        dateValidator.validateDonationDate(reservationRequestDto);
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
        retrieveReservationById(id);
        reservationRepository.deleteById(id);
    }

    @Override
    public ReservationResponseDto getReservationById(Long id) {
        Reservation reservation = retrieveReservationById(id);
        return reservationMapper.mapToReservationResponseDto(reservation);
    }

    @Override
    public List<ReservationResponseDto> getAllReservationsByDonorId(Long donorId) {
        return reservationRepository.findAllByDonorId(donorId).stream()
                .map(reservationMapper::mapToReservationResponseDto)
                .collect(Collectors.toList());
    }

    @Override
    public void setReservationStatusAsAppointmentFinished(Long reservationId) {
        Reservation reservation = retrieveReservationById(reservationId);
        reservation.setIsAppointmentFinished(true);
        reservationRepository.saveAndFlush(reservation);
    }

    private Reservation retrieveReservationById(Long id) {
        return reservationRepository.findById(id)
                .orElseThrow(() -> new BloodDonationCentreException(Error.RESERVATION_NOT_FOUND));
    }


}
