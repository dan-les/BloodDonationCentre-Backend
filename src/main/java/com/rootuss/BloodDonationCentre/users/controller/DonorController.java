package com.rootuss.BloodDonationCentre.users.controller;

import com.rootuss.BloodDonationCentre.users.model.DonorRequestDto;
import com.rootuss.BloodDonationCentre.users.model.DonorResponseDto;
import com.rootuss.BloodDonationCentre.users.service.DonorService;
import com.rootuss.BloodDonationCentre.utill.MessageResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController

@RequestMapping("/api/donor")
public class DonorController {
    @Autowired
    private DonorService donorService;

    @GetMapping("/list")
    @PreAuthorize("hasRole('STAFF')")
    public List<DonorResponseDto> getDonor() {
        return donorService.getAllDonors();
    }

    @GetMapping(value = "/{id}")
    @PreAuthorize("hasRole('STAFF') or @userSecurity.hasProperUserId(authentication, #id)")
    public Optional<DonorResponseDto> getDonorById(@PathVariable Long id) {
        return donorService.loadUserById(id);
    }

    @PreAuthorize("hasRole('STAFF')")
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<MessageResponse> deleteDonor(@PathVariable Long id) {
        donorService.deleteById(id);
        return ResponseEntity.ok(new MessageResponse("Donor delete successfully"));
    }

    @PreAuthorize("hasRole('STAFF')")
    @PutMapping(value = "/{id}")
    public DonorResponseDto putDonor(@PathVariable Long id,
                                     @RequestBody @Valid DonorRequestDto donorRequestDto) {
        return donorService.putDonor(id, donorRequestDto);
    }

}
