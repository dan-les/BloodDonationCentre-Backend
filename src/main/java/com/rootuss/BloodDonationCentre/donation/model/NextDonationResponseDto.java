package com.rootuss.BloodDonationCentre.donation.model;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@Builder
public class NextDonationResponseDto {
    private LocalDate date;
}
