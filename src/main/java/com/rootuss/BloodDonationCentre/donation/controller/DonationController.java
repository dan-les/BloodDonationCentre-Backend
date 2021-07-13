package com.rootuss.BloodDonationCentre.donation.controller;

import com.rootuss.BloodDonationCentre.donation.model.DonationRequestDto;
import com.rootuss.BloodDonationCentre.donation.model.DonationResponseDto;
import com.rootuss.BloodDonationCentre.donation.model.NextDonationRequestDto;
import com.rootuss.BloodDonationCentre.donation.model.NextDonationResponseDto;
import com.rootuss.BloodDonationCentre.donation.service.DonationService;
import com.rootuss.BloodDonationCentre.users.model.DonorResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/donation")
public class DonationController {
    private final DonationService donationService;

    @GetMapping("/list")
    @PreAuthorize("hasRole('STAFF') or hasRole('USER')")
    public List<DonationResponseDto> getDonations(@RequestParam(required = false) String donorId) {
        if (donorId != null) {
            return donationService.getDonationsByDonorId(donorId);
        } else {
            return donationService.getAllDonations();
        }
    }

    @GetMapping("/next")
    @PreAuthorize("hasRole('STAFF') or hasRole('USER')")
    public NextDonationResponseDto getSoonestPossibleDateForNextDonation(@RequestParam Long donorId, String donationType) {
        //albo zwracać rok, miesiąc dzień
        return donationService.getSoonestPossibleDateForNextDonation(donationType, donorId);

    }

    @PreAuthorize("hasRole('STAFF')")
    @PostMapping
    public DonationResponseDto addDonation(@RequestBody DonationRequestDto donationRequestDto) {
        return donationService.addDonation(donationRequestDto);
    }

    @PreAuthorize("hasRole('STAFF')")
    @PutMapping(value = "/{id}")
    public DonorResponseDto putDonation(@PathVariable Long id,
                                        @RequestBody @Valid DonationRequestDto donorRequestDto) {

        return donationService.putDonation(id, donorRequestDto);
    }

}
