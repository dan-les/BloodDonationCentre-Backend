package com.rootuss.BloodDonationCentre.donation.service;

import com.rootuss.BloodDonationCentre.donation.model.DonationRequestDto;
import com.rootuss.BloodDonationCentre.donation.model.DonationResponseDto;
import com.rootuss.BloodDonationCentre.donation.repository.DonationRepository;
import com.rootuss.BloodDonationCentre.users.model.DonorResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DonationServiceImpl implements DonationService {
    private final DonationRepository donationRepository;

    @Override
    public List<DonationResponseDto> getDonationsByDonorId(String donorId) {
        return null;
    }

    @Override
    public List<DonationResponseDto> getAllDonations() {
        var x = donationRepository.findAll();
        return null;
    }

    @Override
    public DonationResponseDto addDonation(DonationRequestDto donationRequestDto) {
        return null;
    }

    @Override
    public DonorResponseDto putDonation(Long id, DonationRequestDto donorRequestDto) {
        return null;
    }

    @Override
    public LocalDate getSoonestPossibleDateForNextDonation() {
        return null;
    }
}
