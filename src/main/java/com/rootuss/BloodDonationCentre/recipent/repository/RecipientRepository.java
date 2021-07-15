package com.rootuss.BloodDonationCentre.recipent.repository;

import com.rootuss.BloodDonationCentre.recipent.model.Recipient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RecipientRepository extends JpaRepository<Recipient, Long> {

    @Override
    List<Recipient> findAll();

    @Override
    Optional<Recipient> findById(Long aLong);

    //    @Query("SELECT d FROM Donation d WHERE d.user = :user ORDER BY d.date DESC")
//    List<Donation> findByUser(Optional<User> user);
}
