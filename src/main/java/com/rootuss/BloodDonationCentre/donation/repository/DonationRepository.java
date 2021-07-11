package com.rootuss.BloodDonationCentre.donation.repository;

import com.rootuss.BloodDonationCentre.donation.model.Donation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DonationRepository extends JpaRepository<Donation, Long> {

    @Override
    List<Donation> findAll();
}
