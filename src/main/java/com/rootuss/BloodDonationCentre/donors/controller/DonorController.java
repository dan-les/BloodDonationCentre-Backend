package com.rootuss.BloodDonationCentre.donors.controller;

import com.rootuss.BloodDonationCentre.donors.model.DonorRequestDto;
import com.rootuss.BloodDonationCentre.donors.model.DonorResponseDto;
import com.rootuss.BloodDonationCentre.donors.service.DonorService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/donor")
public class DonorController {
    private final DonorService donorService;

    @GetMapping("/list")
    @PreAuthorize("hasRole('STAFF')")
    public List<DonorResponseDto> getReservation() {
        return donorService.getAllDonors();
    }


    @PostMapping
    public DonorResponseDto createTutorial(@RequestBody DonorRequestDto donorRequestDto) {
        return donorService.addDonor(donorRequestDto);
    }


}
