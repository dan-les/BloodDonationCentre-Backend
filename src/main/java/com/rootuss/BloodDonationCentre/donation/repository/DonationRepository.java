package com.rootuss.BloodDonationCentre.donation.repository;

import com.rootuss.BloodDonationCentre.blood.model.Blood;
import com.rootuss.BloodDonationCentre.donation.model.Donation;
import com.rootuss.BloodDonationCentre.donation.model.EDonationType;
import com.rootuss.BloodDonationCentre.users.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DonationRepository extends JpaRepository<Donation, Long> {

    @Override
    List<Donation> findAll();

    @Query("SELECT d FROM Donation d WHERE d.user = :user ORDER BY d.date DESC")
    List<Donation> findByUser(User user);

    @Query("SELECT d FROM Donation d WHERE d.user.id = :userId ORDER BY d.date DESC")
    List<Donation> findByAllUserId(Long userId);

    @Query("SELECT distinct d FROM Donation d JOIN d.user u WHERE u.blood = :bloodGroupWithRh " +
            "AND d.donationType = :eDonationType AND d.isReleased = :isReleased")
    List<Donation> findAllByDonationTypeAndIsReleasedAndBloodGroupWithRh(
            EDonationType eDonationType, Boolean isReleased, Blood bloodGroupWithRh);

    @Query("SELECT d FROM Donation d WHERE d.donationType = :eDonationType AND d.isReleased = :isReleased ")
    List<Donation> findAllByDonationTypeAndIsReleased(EDonationType eDonationType, Boolean isReleased);

    @Query("SELECT distinct d FROM Donation d JOIN d.user u WHERE u.blood = :bloodGroupWithRh " +
            "AND d.donationType = :eDonationType")
    List<Donation> findAllByDonationTypeAndBloodGroupWithRh(EDonationType eDonationType, Blood bloodGroupWithRh);

    @Query("SELECT distinct d FROM Donation d JOIN d.user u WHERE u.blood = :bloodGroupWithRh " +
            "AND d.isReleased = :isReleased")
    List<Donation> findAllByIsReleasedAndBloodGroupWithRh(Boolean isReleased, Blood bloodGroupWithRh);

    @Query("SELECT d FROM Donation d WHERE d.donationType = :eDonationType")
    List<Donation> findAllByDonationType(EDonationType eDonationType);

    @Query("SELECT d FROM Donation d WHERE d.isReleased = :isReleased ")
    List<Donation> findAllByIsReleased(Boolean isReleased);

    @Query("SELECT distinct d FROM Donation d JOIN d.user u WHERE u.blood = :bloodGroupWithRh")
    List<Donation> findAllByBloodGroupWithRh(Blood bloodGroupWithRh);
}
