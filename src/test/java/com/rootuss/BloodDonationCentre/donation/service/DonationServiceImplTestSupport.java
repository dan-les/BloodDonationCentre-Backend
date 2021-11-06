package com.rootuss.BloodDonationCentre.donation.service;

import com.rootuss.BloodDonationCentre.blood.model.Blood;
import com.rootuss.BloodDonationCentre.blood.model.EBlood;
import com.rootuss.BloodDonationCentre.users.model.User;

public class DonationServiceImplTestSupport {
    static User createUser(String gender) {
        User user = new User();
        user.setId(1L);
        user.setBlood(new Blood(EBlood.GROUP_AB_RH_MINUS));
        user.setGender(gender);
        user.setPesel("99111203216");
        return user;
    }
}
