package com.rootuss.BloodDonationCentre.donation.service;

import com.rootuss.BloodDonationCentre.blood.model.Blood;
import com.rootuss.BloodDonationCentre.blood.model.EBlood;
import com.rootuss.BloodDonationCentre.blood.utill.BloodMapper;
import com.rootuss.BloodDonationCentre.donation.model.*;
import com.rootuss.BloodDonationCentre.donation.repository.DonationRepository;
import com.rootuss.BloodDonationCentre.donation.utill.DonationMapper;
import com.rootuss.BloodDonationCentre.exception.BloodDonationCentreException;
import com.rootuss.BloodDonationCentre.exception.Error;
import com.rootuss.BloodDonationCentre.recipent.repository.RecipientRepository;
import com.rootuss.BloodDonationCentre.reservation.model.Reservation;
import com.rootuss.BloodDonationCentre.reservation.repository.ReservationRepository;
import com.rootuss.BloodDonationCentre.reservation.service.ReservationService;
import com.rootuss.BloodDonationCentre.users.model.User;
import com.rootuss.BloodDonationCentre.users.repository.UserRepository;
import com.rootuss.BloodDonationCentre.utill.MessageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DonationServiceImpl implements DonationService {
    public static final int INTERVAL_8_WEEKS = 8;
    public static final int INTERVAL_4_WEEKS = 4;
    public static final int INTERVAL_2_WEEKS = 2;
    public static final String WOMAN = "K";
    public static final String MAN = "M";
    public static final int MAX_YEAR_QUANTITY_BLOOD_DONATIONS_WOMAN = 4;
    public static final int MAX_YEAR_QUANTITY_BLOOD_DONATIONS_MAN = 6;
    private static final Boolean IS_RELEASED_FALSE = false;

    private final DonationRepository donationRepository;
    private final ReservationRepository reservationRepository;
    private final ReservationService reservationService;
    private final UserRepository userRepository;
    private final RecipientRepository recipientRepository;
    private final DonationMapper donationMapper;
    private final BloodMapper bloodMapper;

    @Override
    public List<DonationResponseDto> getDonationsByDonorId(Long donorId) {
        return donationRepository.findByAllUserId(donorId)
                .stream()
                .map(donationMapper::mapToDonationResponseDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<DonationResponseDto> getAllDonations() {
        return donationRepository.findAll()
                .stream()
                .map(donationMapper::mapToDonationResponseDto)
                .collect(Collectors.toList());
    }

    @Override
    public DonationResponseDto addDonation(DonationRequestDto donationRequestDto) {
        Donation donation = donationMapper.mapDonationRequestDtoToDonation(donationRequestDto);
        setReservationStatusAsAppointmentFinished(donationRequestDto);
        donation = donationRepository.save(donation);
        return donationMapper.mapToDonationResponseDto(donation);
    }

    @Override
    public NextDonationResponseDto getSoonestPossibleDateForNextDonation(String donationType, Long donorId) {
        User user = (userRepository.findById(donorId))
                .orElseThrow(() -> new BloodDonationCentreException(Error.USER_DONOR_NOT_FOUND));
        EDonationType eDonationType = donationMapper.retrieveEDonationType(donationType);
        List<Donation> donationsByUser = donationRepository.findByUser(user);
        List<Reservation> reservationsByUser = reservationRepository.findAllByDonorId(donorId);
        return retrieveSoonestPossibleDateForNextDonation(donationsByUser, reservationsByUser, user, eDonationType);
    }


         /* ---------------------------------------------------
         WYMAGANY ODSTĘP POMIĘDZY POBRANIAMI:
         ---------------------------------------------------
         pobranie krwi -> kobiety - max 4 razy w roku
         pobranie krwi -> mężczyźni - max 6 razy w roku
         ---------------------------------------------------
         poprzednie pobranie -> kolejne pobranie --- odstęp
         krew -> krew --- 8 tyg.
         krew -> osocze --- 4 tyg.
         osocze -> krew --- 4 tyg.
         osocze -> osocze --- 2 tyg.
         --------------------------------------------------- */
    private NextDonationResponseDto retrieveSoonestPossibleDateForNextDonation(
            List<Donation> donations, List<Reservation> reservations, User user, EDonationType nextDonationType) {
        Optional<Donation> lastDonation = donations.stream().max(Comparator.comparing(Donation::getDate));
        Optional<Reservation> lastReservation = reservations.stream().max(Comparator.comparing(Reservation::getDate));

        long bloodDonationsInCurrentYearQuantity = retrieveBloodDonationsInCurrentYearQuantity(donations);

        if (lastDonation.isPresent()) {
            EDonationType lastDonationType = lastDonation.get().getDonationType();
            LocalDate lastDonationDate = lastDonation.get().getDate();
            LocalDate date;
            if (lastReservation.isPresent()) {
                date = calculateDateWhenLastDonationAndLastReservationIsPresent(
                        user, nextDonationType, lastReservation, bloodDonationsInCurrentYearQuantity,
                        lastDonationType, lastDonationDate);
            } else {
                date = calculateDate(nextDonationType, lastDonationType, lastDonationDate, user, bloodDonationsInCurrentYearQuantity);
            }
            return retrieveResponse(date);
        } else {
            if (lastReservation.isPresent()) {
                EDonationType lastReservationType = lastReservation.get().getDonationType();
                LocalDate lastReservationDate = lastReservation.get().getDate();
                LocalDate date = calculateDate(nextDonationType, lastReservationType, lastReservationDate, user, bloodDonationsInCurrentYearQuantity);
                return retrieveResponse(date);
            } else {
                return retrieveResponse(LocalDate.now());
            }
        }
    }

    private LocalDate calculateDateWhenLastDonationAndLastReservationIsPresent(User user,
                                                                               EDonationType nextDonationType,
                                                                               Optional<Reservation> lastReservation,
                                                                               long bloodDonationsInCurrentYearQuantity,
                                                                               EDonationType lastDonationType,
                                                                               LocalDate lastDonationDate) {
        EDonationType lastReservationType = lastReservation.get().getDonationType();
        LocalDate lastReservationDate = lastReservation.get().getDate();
        LocalDate date;
        var lastDonationOrReservationDate = lastDonationDate.isAfter(lastReservationDate) ? lastDonationDate : lastReservationDate;
        if (lastDonationDate.isBefore(lastReservationDate)) {
            date = calculateIntervalAndReturnDate(nextDonationType, lastReservationType, lastReservationDate);
        } else {
            date = calculateIntervalAndReturnDate(nextDonationType, lastDonationType, lastDonationDate);
        }

        var nextReservationDate = calculateIntervalAndReturnDate(nextDonationType, lastDonationType, lastDonationDate);
        if (maxDonationsQuantityPerYearIsReached(user, nextDonationType, lastDonationOrReservationDate, bloodDonationsInCurrentYearQuantity)) {
            return calculateDateWhenMaxDonationsPerYearIsReached(lastDonationOrReservationDate, nextReservationDate);
        }
        return date;
    }

    private LocalDate calculateDateWhenMaxDonationsPerYearIsReached(LocalDate lastDonationOrReservationDate, LocalDate nextReservationDate) {
        if (nextReservationDate.isBefore(LocalDate.of(lastDonationOrReservationDate.getYear(), 12, 31))) {
            return LocalDate.of(lastDonationOrReservationDate.getYear() + 1, 1, 1);
        } else {
            return nextReservationDate;
        }
    }

    private LocalDate calculateDate(EDonationType nextDonationType, EDonationType lastType, LocalDate lastDate,
                                    User user, long bloodDonationsInCurrentYearQuantity) {
        var nextReservationDate = calculateIntervalAndReturnDate(nextDonationType, lastType, lastDate);

        if (maxDonationsQuantityPerYearIsReached(user, nextDonationType, lastDate, bloodDonationsInCurrentYearQuantity)) {
            return calculateDateWhenMaxDonationsPerYearIsReached(lastDate, nextReservationDate);
        } else {
            return nextReservationDate;
        }
    }

    private LocalDate calculateIntervalAndReturnDate(EDonationType nextDonationType, EDonationType lastType, LocalDate lastDate) {
        if (lastType == EDonationType.BLOOD && nextDonationType == EDonationType.BLOOD) {
            return calculateDateForNextDonation(lastDate, INTERVAL_8_WEEKS);
        } else if (lastType == EDonationType.BLOOD && nextDonationType == EDonationType.PLASMA) {
            return calculateDateForNextDonation(lastDate, INTERVAL_4_WEEKS);
        } else if (lastType == EDonationType.PLASMA && nextDonationType == EDonationType.BLOOD) {
            return calculateDateForNextDonation(lastDate, INTERVAL_4_WEEKS);
        } else {
            return calculateDateForNextDonation(lastDate, INTERVAL_2_WEEKS);
        }
    }

    private NextDonationResponseDto retrieveResponse(LocalDate localDate) {
        return NextDonationResponseDto.builder()
                .date(localDate)
                .build();
    }

    private long retrieveBloodDonationsInCurrentYearQuantity(List<Donation> donations) {
        return donations.stream()
                .filter(donation -> donation.getDonationType() == EDonationType.BLOOD)
                .filter(donation -> donation.getDate().getYear() == LocalDate.now().getYear())
                .count();
    }

    private boolean maxDonationsQuantityPerYearIsReached(User user, EDonationType nextDonationType,
                                                         LocalDate lastDonationDate,
                                                         long bloodDonationsInCurrentYearQuantity) {
        String userGender;
        if (user.getGender() == null) {
            userGender = determineGenderFromFirstName(user);
        } else {
            userGender = user.getGender();
        }

        if (donationsLimitPerYearIsReached(nextDonationType, lastDonationDate, bloodDonationsInCurrentYearQuantity,
                userGender, WOMAN, MAX_YEAR_QUANTITY_BLOOD_DONATIONS_WOMAN)) {
            return true;
        }
        if (donationsLimitPerYearIsReached(nextDonationType, lastDonationDate, bloodDonationsInCurrentYearQuantity,
                userGender, MAN, MAX_YEAR_QUANTITY_BLOOD_DONATIONS_MAN)) {
            return true;
        }
        return false;
    }

    private boolean donationsLimitPerYearIsReached(EDonationType nextDonationType, LocalDate lastDonationDate,
                                                   long bloodDonationsInCurrentYearQuantity, String userGender,
                                                   String gender, int maxYearQuantityBloodDonations) {
        return nextDonationType == EDonationType.BLOOD &&
                userGender.equals(gender) &&
                bloodDonationsInCurrentYearQuantity >= maxYearQuantityBloodDonations;
//                &&
//                lastDonationDate.plusWeeks(INTERVAL_4_WEEKS).getYear() == lastDonationDate.getYear();
    }

    private String determineGenderFromFirstName(User user) {
        String userGender;
        String firstName = user.getFirstName();
        if (firstName.endsWith("a")) {
            userGender = WOMAN;
        } else {
            userGender = MAN;
        }
        return userGender;
    }

    private LocalDate retrieveNextYearDate(int year, int month, int day) {
        return LocalDate.of(year, month, day);
    }

    private LocalDate calculateDateForNextDonation(LocalDate date, int interval) {
        return date.plusWeeks(interval);
    }

    @Override
    public List<DonationResponseDto> getAllByDonationTypeAndIsReleasedAndBloodGroupWithRh(
            String donationType, Boolean isReleased, String bloodGroupWithRh) {

        EDonationType eDonationType = donationMapper.retrieveEDonationType(donationType);
        Blood blood = bloodMapper.retrieveBloodGroupFromBloodName(bloodGroupWithRh);

        return donationRepository.findAllByDonationTypeAndIsReleasedAndBloodGroupWithRh(eDonationType, isReleased, blood)
                .stream()
                .map(donationMapper::mapToDonationResponseDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<DonationResponseDto> getAllByDonationTypeAndIsReleased(String donationType, Boolean isReleased) {
        EDonationType eDonationType = donationMapper.retrieveEDonationType(donationType);
        return donationRepository.findAllByDonationTypeAndIsReleased(eDonationType, isReleased)
                .stream()
                .map(donationMapper::mapToDonationResponseDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<DonationResponseDto> getAllByDonationTypeAndBloodGroupWithRh(String donationType, String bloodGroupWithRh) {
        EDonationType eDonationType = donationMapper.retrieveEDonationType(donationType);
        Blood blood = bloodMapper.retrieveBloodGroupFromBloodName(bloodGroupWithRh);
        return donationRepository.findAllByDonationTypeAndBloodGroupWithRh(eDonationType, blood)
                .stream()
                .map(donationMapper::mapToDonationResponseDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<DonationResponseDto> getAllByDonationType(String donationType) {
        EDonationType eDonationType = donationMapper.retrieveEDonationType(donationType);
        return donationRepository.findAllByDonationType(eDonationType)
                .stream()
                .map(donationMapper::mapToDonationResponseDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<DonationResponseDto> getAllByIsReleasedAndBloodGroupWithRh(Boolean isReleased, String bloodGroupWithRh) {
        Blood blood = bloodMapper.retrieveBloodGroupFromBloodName(bloodGroupWithRh);
        return donationRepository.findAllByIsReleasedAndBloodGroupWithRh(isReleased, blood)
                .stream()
                .map(donationMapper::mapToDonationResponseDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<DonationResponseDto> getAllByIsReleased(Boolean isReleased) {
        return donationRepository.findAllByIsReleased(isReleased)
                .stream()
                .map(donationMapper::mapToDonationResponseDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<DonationResponseDto> getAllByBloodGroupWithRh(String bloodGroupWithRh) {
        Blood blood = bloodMapper.retrieveBloodGroupFromBloodName(bloodGroupWithRh);
        return donationRepository.findAllByBloodGroupWithRh(blood)
                .stream()
                .map(donationMapper::mapToDonationResponseDto)
                .collect(Collectors.toList());
    }

    @Override
    public ResponseEntity<MessageResponse> patchDonation(RecipientChangeRequestDto recipientChangeRequestDto) {

        Donation donation = donationRepository.findById(recipientChangeRequestDto.getId()).orElseThrow(
                () -> new BloodDonationCentreException(Error.DONATION_NOT_FOUND));

        donation.setId(recipientChangeRequestDto.getId());
        donation.setIsReleased(recipientChangeRequestDto.getIsReleased());
        donation.setRecipient(recipientRepository.findById(recipientChangeRequestDto.getRecipientId()).orElseThrow(
                () -> new BloodDonationCentreException(Error.RECIPIENT_NOT_FOUND)));
        donationRepository.save(donation);
        return ResponseEntity.ok(new MessageResponse("Donation patch successfully"));
    }

    @Override
    public List<StatisticsResponseDto> getDonationsStatistics(String donationType) {
        List<StatisticsResponseDto> statisticsResponseDtoArrayList = new ArrayList<>();
        Arrays.asList(EBlood.values())
                .forEach(value -> {
                    statisticsResponseDtoArrayList.add(StatisticsResponseDto.builder()
                            .bloodGroupWithRh(value.getStringName())
                            .quantity(retrieveQuantity(value, donationType))
                            .build());
                });
        return statisticsResponseDtoArrayList;
    }

    private long retrieveQuantity(EBlood value, String donationType) {
        EDonationType eDonationType = donationMapper.retrieveEDonationType(donationType);
        Blood bloodGroupWithRh = bloodMapper.retrieveBloodGroupFromBloodName(value.getStringName());
        return donationRepository.findAllByDonationTypeAndIsReleasedAndBloodGroupWithRh(
                eDonationType, IS_RELEASED_FALSE, bloodGroupWithRh).stream()
                .mapToLong(Donation::getAmount).sum();
    }

    @Override
    public UserStatisticsResponseDto getUserDonationsStatistics(Long donorId) {
        List<Donation> donations = donationRepository.findByAllUserId(donorId);
        long plasmaAmount = donations.stream()
                .filter(donation -> donation.getDonationType() == EDonationType.PLASMA)
                .mapToLong(Donation::getAmount).sum();
        long bloodAmount = donations.stream()
                .filter(donation -> donation.getDonationType() == EDonationType.BLOOD)
                .mapToLong(Donation::getAmount).sum();

        return UserStatisticsResponseDto.builder()
                .blood(bloodAmount)
                .plasma(plasmaAmount)
                .build();
    }

    private void setReservationStatusAsAppointmentFinished(DonationRequestDto donationRequestDto) {
        reservationService.changeReservationStatusAsAppointmentFinished(donationRequestDto.getReservationId());
    }
}
