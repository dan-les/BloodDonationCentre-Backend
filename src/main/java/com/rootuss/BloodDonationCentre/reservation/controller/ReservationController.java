package com.rootuss.BloodDonationCentre.reservation.controller;

import com.rootuss.BloodDonationCentre.reservation.model.AvailableHoursForReservationResponseDto;
import com.rootuss.BloodDonationCentre.reservation.model.ReservationRequestDto;
import com.rootuss.BloodDonationCentre.reservation.model.ReservationResponseDto;
import com.rootuss.BloodDonationCentre.reservation.service.ReservationService;
import com.rootuss.BloodDonationCentre.utill.MessageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/reservation")
@RequiredArgsConstructor
public class ReservationController {
    public static final String RESERVATION_DELETE_SUCCESSFULLY = "Reservation delete successfully";
    private final ReservationService reservationService;

    @GetMapping(value = "/{id}")
    @PreAuthorize("hasRole('STAFF')")
    public ReservationResponseDto getReservationById(@PathVariable Long id) {
        return reservationService.getReservationById(id);
    }

    @GetMapping
    @PreAuthorize("hasRole('STAFF')")
    public List<ReservationResponseDto> getAllReservations(@RequestParam(required = false)
                                                           @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        return date != null ? reservationService.getAllReservationsByDate(date) : reservationService.getAllReservations();
    }

    @GetMapping(value = "/donor/{donorId}")
    @PreAuthorize("hasRole('STAFF') or @userSecurity.loggedUserAbleToRetrieveDataByPassedDonorId(authentication, #donorId)")
    public List<ReservationResponseDto> getAllReservationsByDonorId(@PathVariable Long donorId) {
        return reservationService.getAllReservationsByDonorId(donorId);
    }

    @GetMapping("/available-hours")
    @PreAuthorize("hasRole('STAFF') or hasRole('USER')")
    public List<AvailableHoursForReservationResponseDto> getHoursWithAvailability(@RequestParam
                                                                                  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        return reservationService.getHoursWithAvailability(date);
    }

    @PostMapping
    @PreAuthorize("hasRole('STAFF') or hasRole('USER')")
    public ReservationResponseDto addReservation(@RequestBody @Valid ReservationRequestDto reservationRequestDto) {
        return reservationService.addReservation(reservationRequestDto);
    }

    @DeleteMapping(value = "/{id}")
    @PreAuthorize("hasRole('STAFF') or @userSecurity.isLoggedUserAbleToDeleteChosenReservation(authentication, #id)")
    public ResponseEntity<MessageResponse> deleteReservation(@PathVariable Long id) {
        reservationService.deleteById(id);
        return ResponseEntity.ok(new MessageResponse(RESERVATION_DELETE_SUCCESSFULLY));
    }
}
