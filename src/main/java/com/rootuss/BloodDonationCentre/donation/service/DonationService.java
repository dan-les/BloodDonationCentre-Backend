package com.rootuss.BloodDonationCentre.donation.service;

import com.rootuss.BloodDonationCentre.donation.model.DonationRequestDto;
import com.rootuss.BloodDonationCentre.donation.model.DonationResponseDto;
import com.rootuss.BloodDonationCentre.donation.model.NextDonationResponseDto;
import com.rootuss.BloodDonationCentre.users.model.DonorResponseDto;

import java.util.List;


public interface DonationService {


    List<DonationResponseDto> getDonationsByDonorId(Long donorId);

    List<DonationResponseDto> getAllDonations();

    DonationResponseDto addDonation(DonationRequestDto donationRequestDto);

    DonorResponseDto putDonation(Long id, DonationRequestDto donorRequestDto);

    NextDonationResponseDto getSoonestPossibleDateForNextDonation(String donationType, Long donorId);
}
