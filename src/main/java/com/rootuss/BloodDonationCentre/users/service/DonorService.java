package com.rootuss.BloodDonationCentre.users.service;

import com.rootuss.BloodDonationCentre.users.model.DonorRequestDto;
import com.rootuss.BloodDonationCentre.users.model.DonorResponseDto;

import java.util.List;
import java.util.Optional;

public interface DonorService {

    List<DonorResponseDto> getAllDonors();

    Optional<DonorResponseDto> loadDonorById(Long Id);

    void deleteById(Long id);

    DonorResponseDto putDonor(Long id, DonorRequestDto donorRequestDto);
}
