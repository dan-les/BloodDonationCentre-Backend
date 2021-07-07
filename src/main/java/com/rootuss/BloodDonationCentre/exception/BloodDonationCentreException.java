package com.rootuss.BloodDonationCentre.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class BloodDonationCentreException extends RuntimeException {
    private Error error;
}
