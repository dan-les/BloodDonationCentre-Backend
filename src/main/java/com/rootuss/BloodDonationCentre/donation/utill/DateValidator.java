package com.rootuss.BloodDonationCentre.donation.utill;

import com.rootuss.BloodDonationCentre.donation.service.DonationService;
import com.rootuss.BloodDonationCentre.exception.BloodDonationCentreException;
import com.rootuss.BloodDonationCentre.exception.Error;
import com.rootuss.BloodDonationCentre.reservation.model.ReservationRequestDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class DateValidator {
    private final DonationService donationService;

    @Autowired
    // @Lazy to avoid Circular Dependency
    public DateValidator(@Lazy DonationService donationService) {
        this.donationService = donationService;
    }

    public void validateDonationDate(ReservationRequestDto reservationRequestDto) {
        Long donorId = reservationRequestDto.getDonorId();
        String donationType = reservationRequestDto.getDonationType();
        LocalDate possibleDate = donationService.getSoonestPossibleDateForNextDonation(donationType, donorId).getDate();

        if (reservationRequestDto.getDate().isBefore(possibleDate)) {
            throw new BloodDonationCentreException(Error.WRONG_RESERVATION_DATE);
        }
    }
}
