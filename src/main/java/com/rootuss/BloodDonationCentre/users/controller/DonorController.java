package com.rootuss.BloodDonationCentre.users.controller;

import com.rootuss.BloodDonationCentre.users.model.DonorRequestDto;
import com.rootuss.BloodDonationCentre.users.model.DonorResponseDto;
import com.rootuss.BloodDonationCentre.users.service.DonorService;
import com.rootuss.BloodDonationCentre.utill.MessageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/donor")
@RequiredArgsConstructor
public class DonorController {
    public static final String DONOR_DELETE_SUCCESSFULLY = "Donor delete successfully";
    private final DonorService donorService;

    @GetMapping("/list")
    @PreAuthorize("hasRole('STAFF')")
    public List<DonorResponseDto> getAllDonors() {
        return donorService.getAllDonors();
    }

    @GetMapping(value = "/{id}")
    @PreAuthorize("hasRole('STAFF') or @userSecurity.isLoggedUserAbleToRetrieveReservationsByPassedDonorId(authentication, #id)")
    public Optional<DonorResponseDto> getDonorById(@PathVariable Long id) {
        return donorService.loadDonorById(id);
    }

    @PreAuthorize("hasRole('STAFF')")
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<MessageResponse> deleteDonor(@PathVariable Long id) {
        donorService.deleteById(id);
        return ResponseEntity.ok(new MessageResponse(DONOR_DELETE_SUCCESSFULLY));
    }

    @PreAuthorize("hasRole('STAFF')")
    @PutMapping(value = "/{id}")
    public DonorResponseDto putDonor(@PathVariable Long id,
                                     @RequestBody @Valid DonorRequestDto donorRequestDto) {
        return donorService.putDonor(id, donorRequestDto);
    }

}
