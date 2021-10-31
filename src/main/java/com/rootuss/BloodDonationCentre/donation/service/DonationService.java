package com.rootuss.BloodDonationCentre.donation.service;

import com.rootuss.BloodDonationCentre.donation.model.*;
import com.rootuss.BloodDonationCentre.utill.MessageResponse;
import org.springframework.http.ResponseEntity;

import java.util.List;


public interface DonationService {

    List<DonationResponseDto> getDonationsByDonorId(Long donorId);

    List<DonationResponseDto> getAllDonations();

    DonationResponseDto addDonation(DonationRequestDto donationRequestDto);

    NextDonationResponseDto getSoonestPossibleDateForNextDonation(String donationType, Long donorId);

    List<DonationResponseDto> getAllByDonationTypeAndIsReleasedAndBloodGroupWithRh(String donationType, Boolean isReleased, String bloodGroupWithRh);

    ResponseEntity<MessageResponse> patchDonation(RecipientChangeRequestDto recipientChangeRequestDto);

    List<StatisticsResponseDto> getDonationsStatistics(String donationType);

    UserStatisticsResponseDto getUserDonationsStatistics(Long donorId);
}
