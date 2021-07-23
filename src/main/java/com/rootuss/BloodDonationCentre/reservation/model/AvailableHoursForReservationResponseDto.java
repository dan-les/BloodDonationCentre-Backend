package com.rootuss.BloodDonationCentre.reservation.model;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AvailableHoursForReservationResponseDto {
    private String hour;
    private Boolean disabled;
}
