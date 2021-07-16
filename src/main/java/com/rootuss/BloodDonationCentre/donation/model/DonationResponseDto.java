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
    private Long donorId;
    private String donorFirstName;
    private String donorLastName;
    private Long amount;
    private String donationType;
    private Boolean isReleased;
    private Long recipientId;
    private String recipientName;
}
