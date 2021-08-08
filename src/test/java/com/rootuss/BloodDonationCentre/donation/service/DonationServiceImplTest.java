package com.rootuss.BloodDonationCentre.donation.service;

import com.rootuss.BloodDonationCentre.donation.model.Donation;
import com.rootuss.BloodDonationCentre.donation.model.EDonationType;
import com.rootuss.BloodDonationCentre.donation.repository.DonationRepository;
import com.rootuss.BloodDonationCentre.donation.utill.DonationMapper;
import com.rootuss.BloodDonationCentre.reservation.model.Reservation;
import com.rootuss.BloodDonationCentre.reservation.repository.ReservationRepository;
import com.rootuss.BloodDonationCentre.users.model.User;
import com.rootuss.BloodDonationCentre.users.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
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

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class DonationServiceImplTest {
    public static final int INTERVAL_8_WEEKS = 8;
    public static final int INTERVAL_4_WEEKS = 4;
    public static final int INTERVAL_2_WEEKS = 2;
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


    @BeforeEach
    void setUp() {

    }

    @Test
    void shouldCheckIntervalBetweenBloodAndBloodDonation() {
        // given
        Long userId = 1L;
        EDonationType nextDonationType = EDonationType.BLOOD;
        EDonationType lastDonationType = EDonationType.BLOOD;

        ArrayList<Reservation> reservations = new ArrayList<>();
        List<Donation> donations = new ArrayList<>();

        LocalDate lastDonationDate = LocalDate.of(2021, 5, 10);
        addNewDonation(lastDonationType, donations, lastDonationDate);

        Mockito.when(userRepository.findById(anyLong()))
                .thenReturn(DonationServiceImplTestSupport.createUserMan());
        Mockito.when(donationRepository.findByUser(any(User.class)))
                .thenReturn(donations);
        Mockito.when(reservationRepository.findAllByDonorId(anyLong()))
                .thenReturn(reservations);
        Mockito.when(donationMapper.retrieveEDonationType(anyString()))
                .thenReturn(nextDonationType);

        // when
        LocalDate date = donationService.getSoonestPossibleDateForNextDonation(nextDonationType.getName(), userId).getDate();

        // then
        assertEquals(date, lastDonationDate.plusWeeks(INTERVAL_8_WEEKS));
    }

    @Test
    void shouldCheckIntervalBetweenBloodAndPlasmaDonation() {
        // given
        Long userId = 1L;
        EDonationType nextDonationType = EDonationType.PLASMA;
        EDonationType lastDonationType = EDonationType.BLOOD;

        ArrayList<Reservation> reservations = new ArrayList<>();
        List<Donation> donations = new ArrayList<>();

        LocalDate lastDonationDate = LocalDate.of(2021, 5, 10);
        addNewDonation(lastDonationType, donations, lastDonationDate);

        Mockito.when(userRepository.findById(anyLong()))
                .thenReturn(DonationServiceImplTestSupport.createUserMan());
        Mockito.when(donationRepository.findByUser(any(User.class)))
                .thenReturn(donations);
        Mockito.when(reservationRepository.findAllByDonorId(anyLong()))
                .thenReturn(reservations);
        Mockito.when(donationMapper.retrieveEDonationType(anyString()))
                .thenReturn(nextDonationType);

        // when
        LocalDate date = donationService.getSoonestPossibleDateForNextDonation(nextDonationType.getName(), userId).getDate();

        // then
        assertEquals(date, lastDonationDate.plusWeeks(INTERVAL_4_WEEKS));
    }

    @Test
    void shouldCheckIntervalBetweenPlasmaAndPlasmaDonation() {
        // given
        Long userId = 1L;
        EDonationType nextDonationType = EDonationType.PLASMA;
        EDonationType lastDonationType = EDonationType.PLASMA;

        ArrayList<Reservation> reservations = new ArrayList<>();
        List<Donation> donations = new ArrayList<>();

        LocalDate lastDonationDate = LocalDate.of(2021, 5, 10);
        addNewDonation(lastDonationType, donations, lastDonationDate);

        Mockito.when(userRepository.findById(anyLong()))
                .thenReturn(DonationServiceImplTestSupport.createUserMan());
        Mockito.when(donationRepository.findByUser(any(User.class)))
                .thenReturn(donations);
        Mockito.when(reservationRepository.findAllByDonorId(anyLong()))
                .thenReturn(reservations);
        Mockito.when(donationMapper.retrieveEDonationType(anyString()))
                .thenReturn(nextDonationType);

        // when
        LocalDate date = donationService.getSoonestPossibleDateForNextDonation(nextDonationType.getName(), userId).getDate();

        // then
        assertEquals(date, lastDonationDate.plusWeeks(INTERVAL_2_WEEKS));
    }

    @Test
    void shouldCheckIntervalBetweenPlasmaAndBloodDonation() {
        // given
        Long userId = 1L;
        EDonationType nextDonationType = EDonationType.PLASMA;
        EDonationType lastDonationType = EDonationType.BLOOD;

        ArrayList<Reservation> reservations = new ArrayList<>();
        List<Donation> donations = new ArrayList<>();

        LocalDate lastDonationDate = LocalDate.of(2021, 5, 10);
        addNewDonation(lastDonationType, donations, lastDonationDate);

        Mockito.when(userRepository.findById(anyLong()))
                .thenReturn(DonationServiceImplTestSupport.createUserMan());
        Mockito.when(donationRepository.findByUser(any(User.class)))
                .thenReturn(donations);
        Mockito.when(reservationRepository.findAllByDonorId(anyLong()))
                .thenReturn(reservations);
        Mockito.when(donationMapper.retrieveEDonationType(anyString()))
                .thenReturn(nextDonationType);

        // when
        LocalDate date = donationService.getSoonestPossibleDateForNextDonation(nextDonationType.getName(), userId).getDate();

        // then
        assertEquals(date, lastDonationDate.plusWeeks(INTERVAL_4_WEEKS));
    }

    @Test
    void shouldCheckIntervalBetweenLastBloodDonationReservationAndNextBloodDonation() {
        // given
        Long userId = 1L;
        EDonationType nextDonationType = EDonationType.BLOOD;
        EDonationType lastDonationType = EDonationType.BLOOD;
        EDonationType lastReservationType = EDonationType.BLOOD;

        ArrayList<Reservation> reservations = new ArrayList<>();
        List<Donation> donations = new ArrayList<>();

        LocalDate lastDonationDate = LocalDate.of(2021, 2, 11);
        LocalDate lastReservationDate = LocalDate.of(2021, 9, 1);

        addNewDonation(lastDonationType, donations, lastDonationDate);
        addNewReservation(lastReservationType, reservations, lastReservationDate);

        Mockito.when(userRepository.findById(anyLong()))
                .thenReturn(DonationServiceImplTestSupport.createUserMan());
        Mockito.when(donationRepository.findByUser(any(User.class)))
                .thenReturn(donations);
        Mockito.when(reservationRepository.findAllByDonorId(anyLong()))
                .thenReturn(reservations);
        Mockito.when(donationMapper.retrieveEDonationType(anyString()))
                .thenReturn(nextDonationType);

        // when
        LocalDate date = donationService.getSoonestPossibleDateForNextDonation(nextDonationType.getName(), userId).getDate();

        // then
        assertEquals(date, lastReservationDate.plusWeeks(INTERVAL_8_WEEKS));
    }

    @Test
    void shouldCheckMaxYearBloodDonationsQuantityForManWhenLastDonationDateIsInLastTwoMonthsOfYear() {
        // given
        Long userId = 1L;
        EDonationType nextDonationType = EDonationType.BLOOD;
        EDonationType lastDonationType = EDonationType.BLOOD;

        ArrayList<Reservation> reservations = new ArrayList<>();
        List<Donation> donations = new ArrayList<>();

        addNewDonation(lastDonationType, donations, LocalDate.of(2021, 1, 1));
        addNewDonation(lastDonationType, donations, LocalDate.of(2021, 4, 10));
        addNewDonation(lastDonationType, donations, LocalDate.of(2021, 7, 10));
        addNewDonation(lastDonationType, donations, LocalDate.of(2021, 8, 10));
        addNewDonation(lastDonationType, donations, LocalDate.of(2021, 10, 10));
        addNewDonation(lastDonationType, donations, LocalDate.of(2021, 12, 10));

        Mockito.when(userRepository.findById(anyLong()))
                .thenReturn(DonationServiceImplTestSupport.createUserWoman());
        Mockito.when(donationRepository.findByUser(any(User.class)))
                .thenReturn(donations);
        Mockito.when(reservationRepository.findAllByDonorId(anyLong()))
                .thenReturn(reservations);
        Mockito.when(donationMapper.retrieveEDonationType(anyString()))
                .thenReturn(nextDonationType);

        // when
        LocalDate date = donationService.getSoonestPossibleDateForNextDonation(nextDonationType.getName(), userId).getDate();

        // then
        assertEquals(date, LocalDate.of(2021, 12, 10).plusWeeks(INTERVAL_8_WEEKS));
    }

    @Test
    void shouldCheckMaxYearBloodDonationsQuantityForWomanWhenLastDonationDateIsEarlierBeforeEndOfYear() {
        // given
        Long userId = 1L;
        EDonationType nextDonationType = EDonationType.BLOOD;
        EDonationType lastDonationType = EDonationType.BLOOD;

        ArrayList<Reservation> reservations = new ArrayList<>();
        List<Donation> donations = new ArrayList<>();

        addNewDonation(lastDonationType, donations, LocalDate.of(2021, 1, 1));
        addNewDonation(lastDonationType, donations, LocalDate.of(2021, 2, 10));
        addNewDonation(lastDonationType, donations, LocalDate.of(2021, 3, 10));
        addNewDonation(lastDonationType, donations, LocalDate.of(2021, 4, 10));

        Mockito.when(userRepository.findById(anyLong()))
                .thenReturn(DonationServiceImplTestSupport.createUserWoman());
        Mockito.when(donationRepository.findByUser(any(User.class)))
                .thenReturn(donations);
        Mockito.when(reservationRepository.findAllByDonorId(anyLong()))
                .thenReturn(reservations);
        Mockito.when(donationMapper.retrieveEDonationType(anyString()))
                .thenReturn(nextDonationType);

        // when
        LocalDate date = donationService.getSoonestPossibleDateForNextDonation(nextDonationType.getName(), userId).getDate();

        // then
        assertEquals(date, LocalDate.of(2022, 1, 1));
    }

    private void addNewDonation(EDonationType lastDonationType, List<Donation> donations, LocalDate lastDonationDate) {
        donations.add(Donation.builder()
                .date(lastDonationDate)
                .donationType(lastDonationType)
                .build());
    }

    private void addNewReservation(EDonationType lastReservationType, List<Reservation> reservations, LocalDate lastReservationDate) {
        reservations.add(Reservation.builder()
                .date(lastReservationDate)
                .donationType(lastReservationType)
                .build());
    }
}