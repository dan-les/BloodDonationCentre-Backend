package com.rootuss.BloodDonationCentre.donation.service;

import com.rootuss.BloodDonationCentre.blood.model.Blood;
import com.rootuss.BloodDonationCentre.blood.model.EBlood;
import com.rootuss.BloodDonationCentre.users.model.User;

import java.util.Optional;

public class DonationServiceImplTestSupport {
    static Optional<User> createUserMan() {
        return Optional.ofNullable(User.builder()
                .id(1L)
                .firstName("Adam")
                .lastName("Kowalski")
                .blood(new Blood(EBlood.GROUP_AB_RH_MINUS))
                .email("adam.kowalski@gmail.com")
                .gender("M")
                .pesel("99111203216")
                .build());
    }

    static Optional<User> createUserWoman() {
        return Optional.ofNullable(User.builder()
                .id(1L)
                .firstName("Monika")
                .lastName("Kowalska")
                .blood(new Blood(EBlood.GROUP_AB_RH_MINUS))
                .email("m.kowalska@gmail.com")
                .gender("K")
                .pesel("99111203216")
                .build());
    }
}
