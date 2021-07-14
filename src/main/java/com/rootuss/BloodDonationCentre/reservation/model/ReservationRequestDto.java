package com.rootuss.BloodDonationCentre.reservation.model;

import lombok.Getter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Getter
public class ReservationRequestDto {
    @NotNull(message = "Donor Id can not be blank")
    private Long donorId;
    @NotNull(message = "Date can not be blank")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate date;
    @NotNull(message = "Time can not be blank")
    @DateTimeFormat(iso = DateTimeFormat.ISO.NONE)
    private String time;
    @NotNull(message = "Donation Type can not be blank")
    private String donationType;
}
