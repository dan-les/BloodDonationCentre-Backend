package com.rootuss.BloodDonationCentre.users.service;

import com.rootuss.BloodDonationCentre.exception.BloodDonationCentreException;
import com.rootuss.BloodDonationCentre.exception.Error;
import com.rootuss.BloodDonationCentre.reservation.model.Reservation;
import com.rootuss.BloodDonationCentre.reservation.repository.ReservationRepository;
import com.rootuss.BloodDonationCentre.users.model.DonorRequestDto;
import com.rootuss.BloodDonationCentre.users.model.DonorResponseDto;
import com.rootuss.BloodDonationCentre.users.model.User;
import com.rootuss.BloodDonationCentre.users.repository.UserRepository;
import com.rootuss.BloodDonationCentre.users.roles.model.ERole;
import com.rootuss.BloodDonationCentre.users.util.DonorMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DonorServiceImpl implements DonorService {
    private final UserRepository userRepository;
    private final ReservationRepository reservationRepository;
    private final DonorMapper donorMapper;

    @Override
    public List<DonorResponseDto> getAllDonors() {
        return userRepository.findAllDonors(ERole.ROLE_USER)
                .stream()
                .map(donorMapper::mapToDonorResponseDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public Optional<DonorResponseDto> loadDonorById(Long Id) throws UsernameNotFoundException {
        return Optional.ofNullable(userRepository.findById(Id))
                .orElseThrow(() -> new BloodDonationCentreException(Error.USER_DONOR_NOT_FOUND))
                .map(donorMapper::mapToDonorResponseDto);
    }

    @Override
    public void deleteById(Long id) {
        User user = retrieveUser(id);
        user.getUserDetails().setEnabled(false);
        userRepository.saveAndFlush(user);
        deleteFutureReservations(id);
    }

    @Override
    public DonorResponseDto putDonor(Long id, DonorRequestDto donorRequestDto) {
        User user = retrieveUser(id);
        User userRequest = donorMapper.mapDonorRequestDtoToDonor(donorRequestDto);

        user.getUserDetails().setUsername(userRequest.getUserDetails().getUsername());
        user.setFirstName(userRequest.getFirstName());
        user.setLastName(userRequest.getLastName());
        user.setBlood(userRequest.getBlood());
        user.setEmail(userRequest.getEmail());
        user.setPesel(userRequest.getPesel());
        user.setGender(userRequest.getGender());

        user = userRepository.saveAndFlush(user);
        return donorMapper.mapToDonorResponseDto(user);
    }

    private User retrieveUser(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new BloodDonationCentreException(Error.USER_DONOR_NOT_FOUND));
    }

    private void deleteFutureReservations(Long id) {
        reservationRepository.findAllByDonorId(id).stream()
                .filter(this::isFutureReservation)
                .forEach(reservationRepository::delete);
    }

    private boolean isFutureReservation(Reservation reservation) {
        return reservation.getDate().isAfter(LocalDate.now());
    }
}
