package com.rootuss.BloodDonationCentre.donation.service;

import com.rootuss.BloodDonationCentre.blood.model.Blood;
import com.rootuss.BloodDonationCentre.blood.model.EBlood;
import com.rootuss.BloodDonationCentre.users.model.User;

public class DonationServiceImplTestSupport {
    static User createUser(String gender) {
        return User.builder()
                .id(1L)
                .blood(new Blood(EBlood.GROUP_AB_RH_MINUS))
                .gender(gender)
                .pesel("99111203216")
                .build();
    }
}
