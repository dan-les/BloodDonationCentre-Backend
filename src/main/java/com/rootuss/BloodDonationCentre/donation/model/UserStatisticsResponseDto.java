package com.rootuss.BloodDonationCentre.donation.model;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserStatisticsResponseDto {
    Long blood;
    Long plasma;
}