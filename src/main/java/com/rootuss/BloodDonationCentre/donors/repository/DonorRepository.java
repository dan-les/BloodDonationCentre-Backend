package com.rootuss.BloodDonationCentre.donors.repository;

import com.rootuss.BloodDonationCentre.donors.model.Donor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface DonorRepository extends JpaRepository<Donor, Long> {
    // Eliminate N+1 problem
    @Query("SELECT d FROM Donor d INNER JOIN FETCH d.blood")
    List<Donor> findAllReservations();
}
