package com.rootuss.BloodDonationCentre.donation.service;

import com.rootuss.BloodDonationCentre.donation.model.Donation;
import com.rootuss.BloodDonationCentre.donation.model.EDonationType;
import com.rootuss.BloodDonationCentre.donation.repository.DonationRepository;
import com.rootuss.BloodDonationCentre.donation.utill.DonationMapper;
import com.rootuss.BloodDonationCentre.reservation.model.Reservation;
import com.rootuss.BloodDonationCentre.reservation.repository.ReservationRepository;
import com.rootuss.BloodDonationCentre.users.model.User;
import com.rootuss.BloodDonationCentre.users.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class DonationServiceImplTest {
    private static final int INTERVAL_8_WEEKS = 8;
    private static final int INTERVAL_4_WEEKS = 4;
    private static final int INTERVAL_2_WEEKS = 2;
    private static final String GENDER_MAN = "M";
    private static final String GENDER_WOMAN = "K";

    @Mock
    private UserRepository userRepository;
    @Mock
    private ReservationRepository reservationRepository;
    @Mock
    private DonationRepository donationRepository;
    @Mock
    private DonationMapper donationMapper;
    @InjectMocks
    private DonationServiceImpl donationService;

    @Test
    void shouldCheckIntervalBetweenBloodAndBloodDonation() {
        // given
        User user = DonationServiceImplTestSupport.createUser(GENDER_MAN);
        EDonationType nextDonationType = EDonationType.BLOOD;
        EDonationType lastDonationType = EDonationType.BLOOD;

        List<Reservation> reservations = new ArrayList<>();
        List<Donation> donations = new ArrayList<>();

        LocalDate lastDonationDate = LocalDate.of(2021, 5, 10);
        addNewDonation(lastDonationType, donations, lastDonationDate);

        callMocks(nextDonationType, reservations, donations, user);

        // when
        LocalDate date = donationService.getSoonestPossibleDateForNextDonation(nextDonationType.getName(), user.getId()).getDate();

        // then
        assertNotNull(date);
        assertEquals(date, lastDonationDate.plusWeeks(INTERVAL_8_WEEKS));
    }

    @Test
    void shouldCheckIntervalBetweenBloodAndPlasmaDonation() {
        // given
        User user = DonationServiceImplTestSupport.createUser(GENDER_MAN);
        EDonationType nextDonationType = EDonationType.PLASMA;
        EDonationType lastDonationType = EDonationType.BLOOD;

        List<Reservation> reservations = new ArrayList<>();
        List<Donation> donations = new ArrayList<>();

        LocalDate lastDonationDate = LocalDate.of(2021, 5, 10);
        addNewDonation(lastDonationType, donations, lastDonationDate);

        callMocks(nextDonationType, reservations, donations, user);

        // when
        LocalDate date = donationService.getSoonestPossibleDateForNextDonation(nextDonationType.getName(), user.getId()).getDate();

        // then
        assertNotNull(date);
        assertEquals(date, lastDonationDate.plusWeeks(INTERVAL_4_WEEKS));
    }

    @Test
    void shouldCheckIntervalBetweenPlasmaAndPlasmaDonation() {
        // given
        User user = DonationServiceImplTestSupport.createUser(GENDER_MAN);
        EDonationType nextDonationType = EDonationType.PLASMA;
        EDonationType lastDonationType = EDonationType.PLASMA;

        List<Reservation> reservations = new ArrayList<>();
        List<Donation> donations = new ArrayList<>();

        LocalDate lastDonationDate = LocalDate.of(2021, 5, 10);
        addNewDonation(lastDonationType, donations, lastDonationDate);

        callMocks(nextDonationType, reservations, donations, user);

        // when
        LocalDate date = donationService.getSoonestPossibleDateForNextDonation(nextDonationType.getName(), user.getId()).getDate();

        // then
        assertNotNull(date);
        assertEquals(date, lastDonationDate.plusWeeks(INTERVAL_2_WEEKS));
    }

    @Test
    void shouldCheckIntervalBetweenPlasmaAndBloodDonation() {
        // given
        User user = DonationServiceImplTestSupport.createUser(GENDER_MAN);
        EDonationType nextDonationType = EDonationType.PLASMA;
        EDonationType lastDonationType = EDonationType.BLOOD;

        List<Reservation> reservations = new ArrayList<>();
        List<Donation> donations = new ArrayList<>();

        LocalDate lastDonationDate = LocalDate.of(2021, 5, 10);
        addNewDonation(lastDonationType, donations, lastDonationDate);

        callMocks(nextDonationType, reservations, donations, user);

        // when
        LocalDate date = donationService.getSoonestPossibleDateForNextDonation(nextDonationType.getName(), user.getId()).getDate();

        // then
        assertNotNull(date);
        assertEquals(date, lastDonationDate.plusWeeks(INTERVAL_4_WEEKS));
    }

    @Test
    void shouldCheckIntervalBetweenLastBloodDonationReservationAndNextBloodDonation() {
        // given
        User user = DonationServiceImplTestSupport.createUser(GENDER_MAN);
        EDonationType nextDonationType = EDonationType.BLOOD;
        EDonationType lastDonationType = EDonationType.BLOOD;
        EDonationType lastReservationType = EDonationType.BLOOD;

        List<Reservation> reservations = new ArrayList<>();
        List<Donation> donations = new ArrayList<>();

        LocalDate lastDonationDate = LocalDate.of(2021, 2, 11);
        LocalDate lastReservationDate = LocalDate.of(2021, 9, 1);

        addNewDonation(lastDonationType, donations, lastDonationDate);
        addNewReservation(lastReservationType, reservations, lastReservationDate);

        callMocks(nextDonationType, reservations, donations, user);

        // when
        LocalDate date = donationService.getSoonestPossibleDateForNextDonation(nextDonationType.getName(), user.getId()).getDate();

        // then
        assertNotNull(date);
        assertEquals(date, lastReservationDate.plusWeeks(INTERVAL_8_WEEKS));
    }

    @Test
    void shouldCheckMaxYearBloodDonationsQuantityForManWhenLastDonationDateIsInLastTwoMonthsOfYear() {
        // given
        User user = DonationServiceImplTestSupport.createUser(GENDER_WOMAN);
        EDonationType nextDonationType = EDonationType.BLOOD;
        EDonationType lastDonationType = EDonationType.BLOOD;

        List<Reservation> reservations = new ArrayList<>();
        List<Donation> donations = new ArrayList<>();

        addNewDonation(lastDonationType, donations, LocalDate.of(2021, 1, 1));
        addNewDonation(lastDonationType, donations, LocalDate.of(2021, 4, 10));
        addNewDonation(lastDonationType, donations, LocalDate.of(2021, 7, 10));
        addNewDonation(lastDonationType, donations, LocalDate.of(2021, 8, 10));
        addNewDonation(lastDonationType, donations, LocalDate.of(2021, 10, 10));
        addNewDonation(lastDonationType, donations, LocalDate.of(2021, 12, 10));

        callMocks(nextDonationType, reservations, donations, user);

        // when
        LocalDate date = donationService.getSoonestPossibleDateForNextDonation(nextDonationType.getName(), user.getId()).getDate();

        // then
        assertNotNull(date);
        assertEquals(date, LocalDate.of(2021, 12, 10).plusWeeks(INTERVAL_8_WEEKS));
    }

    @Test
    void shouldCheckMaxYearBloodDonationsQuantityForWomanWhenLastDonationDateIsTwoMonthsBeforeEndOfYear() {
        // given
        User user = DonationServiceImplTestSupport.createUser(GENDER_WOMAN);
        EDonationType nextDonationType = EDonationType.BLOOD;
        EDonationType lastDonationType = EDonationType.BLOOD;

        List<Reservation> reservations = new ArrayList<>();
        List<Donation> donations = new ArrayList<>();

        addNewDonation(lastDonationType, donations, LocalDate.of(2021, 1, 1));
        addNewDonation(lastDonationType, donations, LocalDate.of(2021, 3, 10));
        addNewDonation(lastDonationType, donations, LocalDate.of(2021, 5, 10));
        addNewDonation(lastDonationType, donations, LocalDate.of(2021, 8, 10));

        callMocks(nextDonationType, reservations, donations, user);

        // when
        LocalDate date = donationService.getSoonestPossibleDateForNextDonation(nextDonationType.getName(), user.getId()).getDate();

        // then
        assertNotNull(date);
        assertEquals(date, LocalDate.of(2022, 1, 1));
    }

    private void callMocks(EDonationType nextDonationType, List<Reservation> reservations, List<Donation> donations, User user) {
        Mockito.when(userRepository.findById(anyLong()))
                .thenReturn(Optional.of(user));
        Mockito.when(donationRepository.findByUser(user))
                .thenReturn(donations);
        Mockito.when(reservationRepository.findAllByDonorId(user.getId()))
                .thenReturn(reservations);
        Mockito.when(donationMapper.retrieveEDonationType(anyString()))
                .thenReturn(nextDonationType);
    }

    private void addNewDonation(EDonationType lastDonationType, List<Donation> donations, LocalDate lastDonationDate) {
        Donation donation = Donation.builder()
                .date(lastDonationDate)
                .donationType(lastDonationType)
                .build();
        donations.add(donation);
    }

    private void addNewReservation(EDonationType lastReservationType, List<Reservation> reservations, LocalDate lastReservationDate) {
        Reservation reservation = Reservation.builder()
                .date(lastReservationDate)
                .donationType(lastReservationType)
                .build();
        reservations.add(reservation);
    }
}