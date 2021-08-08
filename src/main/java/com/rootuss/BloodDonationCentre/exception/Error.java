package com.rootuss.BloodDonationCentre.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum Error {

    BLOOD_TYPE_NOT_FOUND("Blood type not found", HttpStatus.NOT_FOUND),
    USER_DONOR_NOT_FOUND("User type not found", HttpStatus.NOT_FOUND),
    RESERVATION_NOT_FOUND("Reservation not found", HttpStatus.NOT_FOUND),
    DONATION_NOT_FOUND("Donation not found", HttpStatus.NOT_FOUND),
    RECIPIENT_NOT_FOUND("Recipient not found", HttpStatus.NOT_FOUND),
    ROLE_NOT_FOUND("Role not found", HttpStatus.NOT_FOUND);

    private final String message;
    private final HttpStatus httpStatus;
}
