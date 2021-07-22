//package com.rootuss.BloodDonationCentre.blood.controller;
//
//import com.rootuss.BloodDonationCentre.blood.model.Blood;
//import com.rootuss.BloodDonationCentre.blood.model.EBlood;
//import com.rootuss.BloodDonationCentre.blood.repository.BloodRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.access.prepost.PreAuthorize;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//@CrossOrigin(origins = "*", maxAge = 3600)
//@RestController
//
//@RequestMapping("/api/blood")
//public class BloodController {
//    @Autowired
//    private BloodRepository bloodRepository;
//
//
////    @PostMapping("/list")
////    @PreAuthorize("hasRole('STAFF')")
////    public List<Blood> getBloodTypes(@RequestBody EBlood eBlood) {
////        return bloodRepository.findByName(eBlood);
////    }
//
//}
