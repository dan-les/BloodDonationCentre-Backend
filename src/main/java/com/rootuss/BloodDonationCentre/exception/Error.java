package com.rootuss.BloodDonationCentre.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum Error {

    BLOOD_TYPE_NOT_FOUND("Blood type not found", HttpStatus.NOT_FOUND),
    USER_DONOR_NOT_FOUND("USER type not found", HttpStatus.NOT_FOUND),
    RESERVATION_NOT_FOUND("Reservation not found", HttpStatus.NOT_FOUND),
    DONATION_NOT_FOUND("Donation not found", HttpStatus.NOT_FOUND),
    RECIPIENT_NOT_FOUND("Recipient not found", HttpStatus.NOT_FOUND),
    ROLE_NOT_FOUND("Role not found", HttpStatus.NOT_FOUND);
//    BAD_REQUEST(null, HttpStatus.BAD_REQUEST),
//    UNAUTHORIZED_REQUEST(null, HttpStatus.UNAUTHORIZED),
//    BAD_LOGIN_CREDENTIALS("Bad login credentials", HttpStatus.UNAUTHORIZED),
//    USERNAME_ALREADY_EXISTS("Username already exists", HttpStatus.CONFLICT),
//    EMAIL_ALREADY_EXISTS("Email already exists", HttpStatus.CONFLICT),
//    INCORRECT_VERIFICATION_TOKEN("The verification token is incorrect", HttpStatus.BAD_REQUEST),
//    VERIFICATION_TOKEN_IS_NOT_VALID("The verification token is not valid", HttpStatus.CONFLICT),
//    USER_NOT_FOUND("User not found", HttpStatus.NOT_FOUND),
//
//    START_DATE_NOT_PASSED("Start date not passed", HttpStatus.BAD_REQUEST),
//    END_DATE_NOT_PASSED("End date not passed", HttpStatus.BAD_REQUEST),
//    END_DATE_IS_BEFORE_START_DATE("End date is before start date", HttpStatus.BAD_REQUEST),
//
//
//    xx("Worksite is already booked", HttpStatus.CONFLICT),
//
//    RESERVATION_NOT_FOUND("Reservation not found", HttpStatus.NOT_FOUND),

    private final String message;
    private final HttpStatus httpStatus;
}
