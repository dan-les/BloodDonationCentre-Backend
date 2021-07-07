package com.rootuss.BloodDonationCentre.donors.service;

import com.rootuss.BloodDonationCentre.donors.model.Donor;
import com.rootuss.BloodDonationCentre.donors.model.DonorRequestDto;
import com.rootuss.BloodDonationCentre.donors.model.DonorResponseDto;
import com.rootuss.BloodDonationCentre.donors.repository.DonorRepository;
import com.rootuss.BloodDonationCentre.donors.util.DonorMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DonorServiceImpl implements DonorService {
    private final DonorRepository donorRepository;
    private final DonorMapper donorMapper;

    @Override
    public List<DonorResponseDto> getAllDonors() {
        return donorRepository.findAllReservations()
                .stream()
                .map(donorMapper::mapToDonorResponseDto)
                .collect(Collectors.toList());
    }

    @Override
    public DonorResponseDto addDonor(DonorRequestDto donorRequestDto) {
        Donor donor = donorMapper.mapDonorRequestDtoToDonor(donorRequestDto);

        donorRepository.save(donor);
        return donorMapper.mapToDonorResponseDto(donor);
    }
}
