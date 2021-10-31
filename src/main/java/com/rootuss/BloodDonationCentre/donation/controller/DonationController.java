package com.rootuss.BloodDonationCentre.donation.controller;

import com.rootuss.BloodDonationCentre.donation.model.*;
import com.rootuss.BloodDonationCentre.donation.service.DonationService;
import com.rootuss.BloodDonationCentre.utill.MessageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/donation")
@RequiredArgsConstructor
public class DonationController {
    private final DonationService donationService;

    @GetMapping
    @PreAuthorize("hasRole('STAFF') or hasRole('USER')")
    public List<DonationResponseDto> getDonations(@RequestParam(required = false) Long donorId,
                                                  @RequestParam(required = false) String donationType,
                                                  @RequestParam(required = false) Boolean isReleased,
                                                  @RequestParam(required = false) String bloodGroupWithRh) {
        if (donorId != null) {
            return donationService.getDonationsByDonorId(donorId);
        } else {
            return donationService.getAllByDonationTypeAndIsReleasedAndBloodGroupWithRh(donationType, isReleased, bloodGroupWithRh);
        }
    }

    @GetMapping("/next")
    @PreAuthorize("hasRole('STAFF') or hasRole('USER')")
    public NextDonationResponseDto getSoonestPossibleDateForNextDonation(@RequestParam Long donorId, String donationType) {
        return donationService.getSoonestPossibleDateForNextDonation(donationType, donorId);
    }

    @PreAuthorize("hasRole('STAFF')")
    @PostMapping
    public DonationResponseDto addDonation(@RequestBody DonationRequestDto donationRequestDto) {
        return donationService.addDonation(donationRequestDto);
    }

    @PreAuthorize("hasRole('STAFF')")
    @PatchMapping
    public ResponseEntity<MessageResponse> patchDonation(@RequestBody RecipientChangeRequestDto recipientChangeRequestDto) {
        return donationService.patchDonation(recipientChangeRequestDto);
    }

    @GetMapping("/statistics")
    @PreAuthorize("hasRole('STAFF')")
    public List<StatisticsResponseDto> getDonationsStatistics(@RequestParam String donationType) {
        return donationService.getDonationsStatistics(donationType);
    }

    @GetMapping("/donor/{donorId}/statistics")
    @PreAuthorize("@userSecurity.isLoggedUserAbleToRetrieveReservationsByPassedDonorId(authentication, #donorId)")
    public UserStatisticsResponseDto getUserDonationsStatistics(@PathVariable Long donorId) {
        return donationService.getUserDonationsStatistics(donorId);
    }
}
