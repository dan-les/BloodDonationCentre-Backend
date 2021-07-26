package com.rootuss.BloodDonationCentre.reservation.controller;

import com.rootuss.BloodDonationCentre.reservation.model.AvailableHoursForReservationResponseDto;
import com.rootuss.BloodDonationCentre.reservation.model.ReservationRequestDto;
import com.rootuss.BloodDonationCentre.reservation.model.ReservationResponseDto;
import com.rootuss.BloodDonationCentre.reservation.service.ReservationService;
import com.rootuss.BloodDonationCentre.utill.MessageResponse;
import org.springframework.beans.factory.annotation.Autowired;
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
public class ReservationController {
    @Autowired
    private ReservationService reservationService;

    @GetMapping("/available-hours/list")
    @PreAuthorize("hasRole('STAFF') or hasRole('USER')")
    public List<AvailableHoursForReservationResponseDto> getHoursWithAvailability(@RequestParam
                                                                                  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        return reservationService.getHoursWithAvailability(date);
    }

    @PreAuthorize("hasRole('STAFF') or hasRole('USER')")
    @PostMapping
    public ReservationResponseDto addReservation(@RequestBody @Valid ReservationRequestDto reservationRequestDto) {
        return reservationService.addReservation(reservationRequestDto);
    }

    @GetMapping("/list")
    @PreAuthorize("hasRole('STAFF')")
    public List<ReservationResponseDto> getAllReservations(@RequestParam(required = false)
                                                           @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        if (date != null) {
            return reservationService.getAllReservationsByDate(date);
        } else {
            return reservationService.getAllReservations();
        }
    }

    @GetMapping(value = "list/donor/{donorId}")
    @PreAuthorize("hasRole('STAFF') or @userSecurity.hasProperUserId(authentication, #donorId)")
    public List<ReservationResponseDto> getAllReservationsByDonorId(@PathVariable Long donorId) {
        return reservationService.getAllReservationsByDonorId(donorId);
    }

    @PreAuthorize("hasRole('STAFF') or @userSecurity.hasReservationProperUserId(authentication, #id)")
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<MessageResponse> deleteReservation(@PathVariable Long id) {
        reservationService.deleteById(id);
        return ResponseEntity.ok(new MessageResponse("Reservation delete successfully"));
    }

    @PreAuthorize("hasRole('STAFF')")
    @GetMapping(value = "/{id}")
    public ReservationResponseDto getReservationById(@PathVariable Long id) {
        return reservationService.getReservationById(id);
    }
}
