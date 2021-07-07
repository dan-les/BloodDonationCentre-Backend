package com.rootuss.BloodDonationCentre.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class ValidationErrorInfo {
    List<String> errors;
}
