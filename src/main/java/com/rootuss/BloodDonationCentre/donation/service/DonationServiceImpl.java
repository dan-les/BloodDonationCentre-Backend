package com.rootuss.BloodDonationCentre.donation.service;

import com.rootuss.BloodDonationCentre.donation.model.DonationRequestDto;
import com.rootuss.BloodDonationCentre.donation.model.DonationResponseDto;
import com.rootuss.BloodDonationCentre.donation.model.NextDonationResponseDto;
import com.rootuss.BloodDonationCentre.donation.repository.DonationRepository;
import com.rootuss.BloodDonationCentre.exception.BloodDonationCentreException;
import com.rootuss.BloodDonationCentre.exception.Error;
import com.rootuss.BloodDonationCentre.users.model.DonorResponseDto;
import com.rootuss.BloodDonationCentre.users.model.User;
import com.rootuss.BloodDonationCentre.users.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DonationServiceImpl implements DonationService {
    private final DonationRepository donationRepository;
    private final UserRepository userRepository;

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
    public NextDonationResponseDto getSoonestPossibleDateForNextDonation(String donationType, Long donorId) {
        Optional<User> user = Optional.ofNullable(userRepository.findById(donorId))
                .orElseThrow(() -> new BloodDonationCentreException(Error.USER_DONOR_NOT_FOUND));
        var donations = donationRepository.findByUser(user);

        var gender = user.get().getGender();

        var dummyDate = NextDonationResponseDto.builder().date(
                LocalDate.of(2021, 7, 30)).build();

        return dummyDate;
    }
}
