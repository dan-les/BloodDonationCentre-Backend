package com.rootuss.BloodDonationCentre.blood.controller;

import com.rootuss.BloodDonationCentre.blood.model.Blood;
import com.rootuss.BloodDonationCentre.blood.repository.BloodRepository;
import com.rootuss.BloodDonationCentre.blood.model.EBlood;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/blood")
public class BloodController {
    private final BloodRepository bloodRepository;

    @PostMapping("/list")
    @PreAuthorize("hasRole('STAFF')")
    public List<Blood> getReservation(@RequestBody EBlood eBlood) {
        return bloodRepository.findByName(eBlood);
    }

}
