package com.rootuss.BloodDonationCentre.reservation.model;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ReservationResponseDto {
    private Long id;
    private Long donorId;
    private String donorFirstName;
    private String donorLastName;
    private String pesel;
    private String date;
    private String time;
    private String donationType;
    private Boolean isAppointmentFinished;
}
