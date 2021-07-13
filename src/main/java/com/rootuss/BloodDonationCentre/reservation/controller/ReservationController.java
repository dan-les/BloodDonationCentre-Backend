package com.rootuss.BloodDonationCentre.reservation.controller;

import com.rootuss.BloodDonationCentre.donation.model.DonationResponseDto;
import com.rootuss.BloodDonationCentre.reservation.model.HoursResponseDto;
import com.rootuss.BloodDonationCentre.reservation.service.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

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
//    @PreAuthorize("hasRole('STAFF')")
//    @PostMapping
//    public DonationResponseDto addDonation(@RequestBody DonationRequestDto donationRequestDto) {
//        return donationService.addDonation(donationRequestDto);
//    }
//
//    @PreAuthorize("hasRole('STAFF')")
//    @PutMapping(value = "/{id}")
//    public DonorResponseDto putDonation(@PathVariable Long id,
//                                        @RequestBody @Valid DonationRequestDto donorRequestDto) {
//
//        return donationService.putDonation(id, donorRequestDto);
//    }

}
