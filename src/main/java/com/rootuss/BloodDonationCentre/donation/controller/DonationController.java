package com.rootuss.BloodDonationCentre.donation.controller;

import com.rootuss.BloodDonationCentre.donation.model.*;
import com.rootuss.BloodDonationCentre.donation.service.DonationService;
import com.rootuss.BloodDonationCentre.utill.MessageResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/donation")
public class DonationController {
    @Autowired
    private DonationService donationService;

    @GetMapping("/list")
    @PreAuthorize("hasRole('STAFF') or hasRole('USER')")
    public List<DonationResponseDto> getDonations(@RequestParam(required = false) Long donorId,
                                                  @RequestParam(required = false) String donationType,
                                                  @RequestParam(required = false) Boolean isReleased,
                                                  @RequestParam(required = false) String bloodGroupWithRh) {
        if (donorId != null) {
            return donationService.getDonationsByDonorId(donorId);
        } else {
            if (donationType != null) {
                if (isReleased != null) {
                    if (bloodGroupWithRh != null) {
                        return donationService.getAllByDonationTypeAndIsReleasedAndBloodGroupWithRh(donationType, isReleased, bloodGroupWithRh);
                    } else {
                        return donationService.getAllByDonationTypeAndIsReleased(donationType, isReleased);
                    }
                } else {
                    if (bloodGroupWithRh != null) {
                        return donationService.getAllByDonationTypeAndBloodGroupWithRh(donationType, bloodGroupWithRh);
                    } else {
                        return donationService.getAllByDonationType(donationType);
                    }
                }
            } else {
                if (isReleased != null) {
                    if (bloodGroupWithRh != null) {
                        return donationService.getAllByIsReleasedAndBloodGroupWithRh(isReleased, bloodGroupWithRh);
                    } else {
                        return donationService.getAllByIsReleased(isReleased);
                    }
                } else {
                    if (bloodGroupWithRh != null) {
                        return donationService.getAllByBloodGroupWithRh(bloodGroupWithRh);
                    } else {
                        return donationService.getAllDonations();
                    }
                }
            }
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
    @PutMapping(value = "/{id}")
    public DonationResponseDto putDonation(@PathVariable Long id,
                                           @RequestBody @Valid DonationRequestDto donorRequestDto) {
        return donationService.putDonation(id, donorRequestDto);
    }

    @PreAuthorize("hasRole('STAFF')")
    @PatchMapping
    public ResponseEntity<MessageResponse> patchDonation(@RequestBody RecipientChangeRequestDto recipientChangeRequestDto) {
        return donationService.patchDonation(recipientChangeRequestDto);
    }

    @GetMapping("/statistics")
    @PreAuthorize("hasRole('STAFF')")
    public List<StatisticsResponseDto> getBloodStatistics() {
        return donationService.getDonationsStatistics();
    }

    @GetMapping("/donor/{donorId}/statistics")
    @PreAuthorize("hasRole('USER')")
    public UserStatisticsResponseDto getUserDonationsStatistics(@PathVariable Long donorId) {
        return donationService.getUserDonationsStatistics(donorId);
    }
}
