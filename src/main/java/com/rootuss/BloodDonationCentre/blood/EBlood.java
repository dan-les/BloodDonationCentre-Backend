package com.rootuss.BloodDonationCentre.blood;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum EBlood {
    GROUP_A_RH_PLUS("A Rh+"),
    GROUP_A_RH_MINUS("A Rh-"),
    GROUP_B_RH_PLUS("B Rh+"),
    GROUP_B_RH_MINUS("B Rh-"),
    GROUP_AB_RH_PLUS("AB Rh+"),
    GROUP_AB_RH_MINUS("AB Rh-"),
    GROUP_0_RH_PLUS("0 Rh+"),
    GROUP_0_RH_MINUS("0 Rh-");

    String name;
}
