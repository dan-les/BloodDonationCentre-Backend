package com.rootuss.BloodDonationCentre.donation.model;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserStatisticsResponseDto {
    private Long blood;
    private Long plasma;
}