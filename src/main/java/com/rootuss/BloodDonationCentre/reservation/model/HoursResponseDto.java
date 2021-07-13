package com.rootuss.BloodDonationCentre.reservation.model;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class HoursResponseDto {
    private String hour;
    private Boolean disabled;
}
