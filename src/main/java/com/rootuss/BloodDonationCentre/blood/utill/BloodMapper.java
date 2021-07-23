package com.rootuss.BloodDonationCentre.blood.utill;

import com.rootuss.BloodDonationCentre.blood.model.Blood;
import com.rootuss.BloodDonationCentre.blood.model.EBlood;
import com.rootuss.BloodDonationCentre.blood.repository.BloodRepository;
import com.rootuss.BloodDonationCentre.exception.BloodDonationCentreException;
import com.rootuss.BloodDonationCentre.exception.Error;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class BloodMapper {
    @Autowired
    private BloodRepository bloodRepository;

    public Blood retrieveBloodGroupFromBloodName(String bloodName) {
        var bloodNameEnum = Arrays.stream(EBlood.values()).filter(b -> b.getStringName().equals(bloodName)).findFirst()
                .orElseThrow(() -> new BloodDonationCentreException(Error.BLOOD_TYPE_NOT_FOUND));


        return bloodRepository.findByName(bloodNameEnum)
                .stream()
                .findFirst()
                .orElseThrow(() -> new BloodDonationCentreException(Error.BLOOD_TYPE_NOT_FOUND));
    }
}
