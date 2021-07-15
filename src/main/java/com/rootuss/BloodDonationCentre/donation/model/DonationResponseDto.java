package com.rootuss.BloodDonationCentre.donation.model;

import lombok.Builder;
import lombok.Getter;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDate;

@Getter
@Builder
public class DonationResponseDto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDate date;
    private String donorId;
    private Long amount;
    private String donationType;
    private Boolean isReleased;
    private String recipientId;
}
