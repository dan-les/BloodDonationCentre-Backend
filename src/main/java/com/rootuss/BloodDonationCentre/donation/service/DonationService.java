package com.rootuss.BloodDonationCentre.donation.service;

import com.rootuss.BloodDonationCentre.donation.model.*;
import com.rootuss.BloodDonationCentre.users.model.DonorResponseDto;
import com.rootuss.BloodDonationCentre.utill.MessageResponse;

import java.util.List;


public interface DonationService {


    List<DonationResponseDto> getDonationsByDonorId(Long donorId);

    List<DonationResponseDto> getAllDonations();

    DonationResponseDto addDonation(DonationRequestDto donationRequestDto);

    DonorResponseDto putDonation(Long id, DonationRequestDto donorRequestDto);

    NextDonationResponseDto getSoonestPossibleDateForNextDonation(String donationType, Long donorId);

    List<DonationResponseDto> getAllByDonationTypeAndIsReleasedAndBloodGroupWithRh(String donationType, Boolean isReleased, String bloodGroupWithRh);

    List<DonationResponseDto> getAllByDonationTypeAndIsReleased(String donationType, Boolean isReleased);

    List<DonationResponseDto> getAllByDonationTypeAndBloodGroupWithRh(String donationType, String bloodGroupWithRh);

    List<DonationResponseDto> getAllByDonationType(String donationType);

    List<DonationResponseDto> getAllByIsReleasedAndBloodGroupWithRh(Boolean isReleased, String bloodGroupWithRh);

    List<DonationResponseDto> getAllByIsReleased(Boolean isReleased);

    List<DonationResponseDto> getAllByBloodGroupWithRh(String bloodGroupWithRh);

    MessageResponse patchDonation(RecipientChangeRequestDto recipientChangeRequestDto);

    List<StatisticsResponseDto> getDonationsStatistics();

    UserStatisticsResponseDto getUserDonationsStatistics(Long donorId);
}
