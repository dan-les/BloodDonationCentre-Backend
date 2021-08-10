package com.rootuss.BloodDonationCentre.donation.model;

import lombok.Getter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Getter
public class DonationRequestDto {
    @NotNull(message = "Donor Id can not be blank")
    private Long donorId;
    @NotNull(message = "Type of donation can not be blank")
    private String donationType;
    @NotNull(message = "Donation size can not be blank")
    private Long amount;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate date;
    private Boolean isReleased;
    private Long recipientId;
    private Long reservationId;
}
