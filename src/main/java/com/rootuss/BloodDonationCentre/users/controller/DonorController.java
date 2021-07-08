package com.rootuss.BloodDonationCentre.users.controller;

import com.rootuss.BloodDonationCentre.users.model.DonorRequestDto;
import com.rootuss.BloodDonationCentre.users.model.DonorResponseDto;
import com.rootuss.BloodDonationCentre.users.service.DonorService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/donor")
public class DonorController {
    private final DonorService donorService;

    @GetMapping("/list")
    @PreAuthorize("hasRole('STAFF')")
    public List<DonorResponseDto> getDonor() {
        return donorService.getAllDonors();
    }


    @PostMapping
    public DonorResponseDto createTutorial(@RequestBody DonorRequestDto donorRequestDto) {
        return donorService.addDonor(donorRequestDto);
    }

    @GetMapping("/id")
    @PreAuthorize("hasRole('STAFF')")
    public Optional<DonorResponseDto> getDonorById(@RequestParam Long donorId) {
        return donorService.loadUserById(donorId);
    }


}
