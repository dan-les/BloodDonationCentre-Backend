package com.rootuss.BloodDonationCentre.reservation.controller;

import com.rootuss.BloodDonationCentre.reservation.model.HoursResponseDto;
import com.rootuss.BloodDonationCentre.reservation.model.ReservationRequestDto;
import com.rootuss.BloodDonationCentre.reservation.model.ReservationResponseDto;
import com.rootuss.BloodDonationCentre.reservation.service.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/reservation")
public class ReservationController {
    private final ReservationService reservationService;

    @GetMapping("/hours/list")
    @PreAuthorize("hasRole('STAFF') or hasRole('USER')")
    public List<HoursResponseDto> getHoursWithAvailability(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        return reservationService.getHoursWithAvailability(date);
    }

    //
//    @GetMapping("/next")
//    @PreAuthorize("hasRole('STAFF') or hasRole('USER')")
//    public NextDonationResponseDto getSoonestPossibleDateForNextDonation(@RequestParam Long donorId, String donationType) {
//        //albo zwracać rok, miesiąc dzień
//        return donationService.getSoonestPossibleDateForNextDonation(donationType, donorId);
//
//    }
//
    @PreAuthorize("hasRole('STAFF') or hasRole('USER')")
    @PostMapping
    public ReservationResponseDto addReservation(@RequestBody @Valid ReservationRequestDto reservationRequestDto) {
        return reservationService.addReservation(reservationRequestDto);
    }

    @GetMapping("/list")
    @PreAuthorize("hasRole('STAFF')")
    public List<ReservationResponseDto> getReservations(@RequestParam(required = false)
                                                        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        if (date != null) {
            return reservationService.getAllReservationsByDate(date);
        } else {
            return reservationService.getAllReservations();
        }
    }

//
//    @PreAuthorize("hasRole('STAFF')")
//    @PutMapping(value = "/{id}")
//    public DonorResponseDto putDonation(@PathVariable Long id,
//                                        @RequestBody @Valid DonationRequestDto donorRequestDto) {
//
//        return donationService.putDonation(id, donorRequestDto);
//    }

}
