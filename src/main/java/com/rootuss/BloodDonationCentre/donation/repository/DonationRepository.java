package com.rootuss.BloodDonationCentre.donation.repository;

import com.rootuss.BloodDonationCentre.donation.model.Donation;
import com.rootuss.BloodDonationCentre.users.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DonationRepository extends JpaRepository<Donation, Long> {

    @Override
    List<Donation> findAll();

    @Query("SELECT d FROM Donation d WHERE d.user = :user ORDER BY d.date DESC")
    List<Donation> findByUser(Optional<User> user);

    @Query("SELECT d FROM Donation d WHERE d.user.id = :userId ORDER BY d.date DESC")
    List<Donation> findByAllUserId(Long userId);


}
