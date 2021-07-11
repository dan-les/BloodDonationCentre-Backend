package com.rootuss.BloodDonationCentre.donation.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum EDonationType {
    BLOOD("blood"),
    PLASMA("plasma");

    String name;
}
