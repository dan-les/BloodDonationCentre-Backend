package com.rootuss.BloodDonationCentre.reservation.repository;

import com.rootuss.BloodDonationCentre.reservation.model.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;


public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    @Query("SELECT r FROM Reservation r WHERE r.time = :time AND r.date = :date")
    List<Reservation> getAllByTimeAndDate(LocalTime time, LocalDate date);

    @Query("SELECT r FROM Reservation r WHERE r.date = :date")
    List<Reservation> findAllByDate(LocalDate date);
}