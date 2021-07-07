package com.rootuss.BloodDonationCentre.donors.service;

import com.rootuss.BloodDonationCentre.donors.model.DonorRequestDto;
import com.rootuss.BloodDonationCentre.donors.model.DonorResponseDto;

import java.util.List;

public interface DonorService {
    List<DonorResponseDto> getAllDonors();

    DonorResponseDto addDonor(DonorRequestDto donorRequestDto);
}
