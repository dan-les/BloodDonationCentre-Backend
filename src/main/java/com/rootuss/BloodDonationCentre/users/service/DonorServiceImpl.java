package com.rootuss.BloodDonationCentre.users.service;

import com.rootuss.BloodDonationCentre.roles.model.ERole;
import com.rootuss.BloodDonationCentre.users.model.DonorRequestDto;
import com.rootuss.BloodDonationCentre.users.model.DonorResponseDto;
import com.rootuss.BloodDonationCentre.users.model.User;
import com.rootuss.BloodDonationCentre.users.repository.UserRepository;
import com.rootuss.BloodDonationCentre.users.util.DonorMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DonorServiceImpl implements DonorService {
    private final UserRepository userRepository;
    private final DonorMapper donorMapper;

    @Override
    public List<DonorResponseDto> getAllDonors() {
        return userRepository.findAllDonors(ERole.ROLE_USER)
                .stream()
                .map(donorMapper::mapToDonorResponseDto)
                .collect(Collectors.toList());
    }

    @Override
    public DonorResponseDto addDonor(DonorRequestDto donorRequestDto) {
        User user = donorMapper.mapDonorRequestDtoToDonor(donorRequestDto);

        userRepository.save(user);
        return donorMapper.mapToDonorResponseDto(user);
    }

    @Override
    @Transactional
    public Optional<DonorResponseDto> loadUserById(Long Id) throws UsernameNotFoundException {
        return Optional.ofNullable(userRepository.findById(Id)).orElseThrow()
                .map(donorMapper::mapToDonorResponseDto);
    }
}
