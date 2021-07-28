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
import com.rootuss.BloodDonationCentre.users.model.User;
import com.rootuss.BloodDonationCentre.users.repository.UserRepository;
import com.rootuss.BloodDonationCentre.users.util.DonorMapper;
import com.rootuss.BloodDonationCentre.utill.MessageResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DonationServiceImpl implements DonationService {
    public static final int INTERVAL_8_WEEKS = 8;
    public static final int INTERVAL_4_WEEKS = 4;
    public static final int INTERVAL_2_WEEKS = 2;
    public static final String WOMAN = "K";
    public static final String MAN = "M";
    public static final int MAX_YEAR_QUANTITY_BLOOD_DONATIONS_WOMAN = 4;
    public static final int MAX_YEAR_QUANTITY_BLOOD_DONATIONS_MAN = 6;
    private static final Boolean IS_RELEASED_FALSE = false;
    @Autowired
    private DonationRepository donationRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RecipientRepository recipientRepository;
    @Autowired
    private DonationMapper donationMapper;
    @Autowired
    private DonorMapper donorMapper;
    @Autowired
    private BloodMapper bloodMapper;


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
        var donation = donationMapper.mapDonationRequestDtoToDonation(donationRequestDto);
        donation = donationRepository.save(donation);
        return donationMapper.mapToDonationResponseDto(donation);
    }

    @Override
    public DonationResponseDto putDonation(Long id, DonationRequestDto donorRequestDto) {
        // TODO
        return null;
    }

    @Override
    public NextDonationResponseDto getSoonestPossibleDateForNextDonation(String donationType, Long donorId) {
        Optional<User> user = Optional.ofNullable(userRepository.findById(donorId))
                .orElseThrow(() -> new BloodDonationCentreException(Error.USER_DONOR_NOT_FOUND));
        EDonationType eDonationType = donationMapper.retrieveEDonationType(donationType);
        return retrieveSoonestPossibleDateForNextDonation(donationRepository.findByUser(user), user, eDonationType);

    }

    private NextDonationResponseDto retrieveSoonestPossibleDateForNextDonation(
            List<Donation> donations, Optional<User> user, EDonationType nextDonationType) {
        // pobranie krwi -> kobiety - max 4 razy w roku
        // pobranie krwi -> mężczyźni - max 6 razy w roku

        // poprzednie pobranie -> kolejne pobranie --- odstęp
        // krew -> krew --- 8 tyg.
        // krew -> osocze --- 4 tyg.
        // osocze -> krew --- 4 tyg.
        // osocze -> osocze --- 2 tyg.
        Optional<Donation> lastDonation = donations.stream().findFirst();
        if (lastDonation.isEmpty()) {
            return NextDonationResponseDto.builder()
                    .date(LocalDate.now())
                    .build();
        } else {
            var lastDonationType = lastDonation.get().getDonationType();
            var lastDonationDate = lastDonation.get().getDate();

            long bloodDonationsInCurrentYearQuantity = retrieveBloodDonationsInCurrentYearQuantity(donations);

            if (checkMaxDonationsQuantityPerYear(user, nextDonationType, lastDonationDate, bloodDonationsInCurrentYearQuantity))
                return retrieveNextYearDate(lastDonationDate.getYear() + 1, 1, 1);
            if (lastDonationType == EDonationType.BLOOD && nextDonationType == EDonationType.BLOOD) {
                return calculateDateForNextDonation(lastDonationDate, INTERVAL_8_WEEKS);
            } else if (lastDonationType == EDonationType.BLOOD && nextDonationType == EDonationType.PLASMA) {
                return calculateDateForNextDonation(lastDonationDate, INTERVAL_4_WEEKS);
            } else if (lastDonationType == EDonationType.PLASMA && nextDonationType == EDonationType.BLOOD) {
                return calculateDateForNextDonation(lastDonationDate, INTERVAL_4_WEEKS);
            } else {
                return calculateDateForNextDonation(lastDonationDate, INTERVAL_2_WEEKS);
            }
        }
    }

    private long retrieveBloodDonationsInCurrentYearQuantity(List<Donation> donations) {
        return donations.stream()
                .filter(donation -> donation.getDonationType() == EDonationType.BLOOD)
                .filter(donation -> donation.getDate().getYear() == LocalDate.now().getYear())
                .count();
    }

    private boolean checkMaxDonationsQuantityPerYear(Optional<User> user, EDonationType nextDonationType, LocalDate lastDonationDate, long bloodDonationsInCurrentYearQuantity) {
        if (nextDonationType == EDonationType.BLOOD &&
                user.get().getGender().equals(WOMAN) &&
                bloodDonationsInCurrentYearQuantity >= MAX_YEAR_QUANTITY_BLOOD_DONATIONS_WOMAN &&
                lastDonationDate.plusWeeks(INTERVAL_4_WEEKS).getYear() == lastDonationDate.getYear()) {
            return true;
        }
        if (nextDonationType == EDonationType.BLOOD &&
                user.get().getGender().equals(MAN) &&
                bloodDonationsInCurrentYearQuantity >= MAX_YEAR_QUANTITY_BLOOD_DONATIONS_MAN &&
                lastDonationDate.plusWeeks(INTERVAL_4_WEEKS).getYear() == lastDonationDate.getYear()) {
            return true;
        }
        return false;
    }

    private NextDonationResponseDto retrieveNextYearDate(int year, int month, int day) {
        return NextDonationResponseDto.builder()
                .date(LocalDate.of(year, month, day))
                .build();
    }

    private NextDonationResponseDto calculateDateForNextDonation(LocalDate date, int interval) {
        return NextDonationResponseDto.builder()
                .date(date.plusWeeks(interval))
                .build();
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

        var donation = donationRepository.findById(recipientChangeRequestDto.getId()).orElseThrow(
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
        var donations = donationRepository.findByAllUserId(donorId);
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
}
