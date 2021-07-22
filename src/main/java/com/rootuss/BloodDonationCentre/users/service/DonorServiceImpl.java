package com.rootuss.BloodDonationCentre.users.service;

import com.rootuss.BloodDonationCentre.exception.BloodDonationCentreException;
import com.rootuss.BloodDonationCentre.exception.Error;
import com.rootuss.BloodDonationCentre.roles.model.ERole;
import com.rootuss.BloodDonationCentre.users.model.DonorRequestDto;
import com.rootuss.BloodDonationCentre.users.model.DonorResponseDto;
import com.rootuss.BloodDonationCentre.users.model.User;
import com.rootuss.BloodDonationCentre.users.repository.UserRepository;
import com.rootuss.BloodDonationCentre.users.util.DonorMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DonorServiceImpl implements DonorService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private DonorMapper donorMapper;

    @Override
    public List<DonorResponseDto> getAllDonors() {
        return userRepository.findAllDonors(ERole.ROLE_USER)
                .stream()
                .map(donorMapper::mapToDonorResponseDto)
                .collect(Collectors.toList());
    }

//    @Override
//    public DonorResponseDto addDonor(DonorRequestDto donorRequestDto) {
//        User user = donorMapper.mapDonorRequestDtoToDonor(donorRequestDto);
//
//        userRepository.save(user);
//        return donorMapper.mapToDonorResponseDto(user);
//    }

    @Override
    @Transactional
    public Optional<DonorResponseDto> loadUserById(Long Id) throws UsernameNotFoundException {
        return Optional.ofNullable(userRepository.findById(Id))
                .orElseThrow(() -> new BloodDonationCentreException(Error.USER_DONOR_NOT_FOUND))
                .map(donorMapper::mapToDonorResponseDto);
    }

    @Override
    public void deleteById(Long id) {
        userRepository.findById(id)
                .orElseThrow(() -> new BloodDonationCentreException(Error.USER_DONOR_NOT_FOUND));
        userRepository.deleteById(id);
    }

    @Override
    public DonorResponseDto putDonor(Long id, DonorRequestDto donorRequestDto) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new BloodDonationCentreException(Error.USER_DONOR_NOT_FOUND));

        User userRequest = donorMapper.mapDonorRequestDtoToDonor(donorRequestDto);

        user.setUsername(userRequest.getUsername());
        user.setFirstName(userRequest.getFirstName());
        user.setLastName(userRequest.getLastName());
        user.setBlood(userRequest.getBlood());
        user.setEmail(userRequest.getEmail());
        user.setPesel(userRequest.getPesel());
        user.setGender(userRequest.getGender());

        user = userRepository.saveAndFlush(user);
        return donorMapper.mapToDonorResponseDto(user);
    }
}
