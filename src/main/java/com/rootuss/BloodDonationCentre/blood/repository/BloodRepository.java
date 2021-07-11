package com.rootuss.BloodDonationCentre.blood.repository;

import com.rootuss.BloodDonationCentre.blood.model.Blood;
import com.rootuss.BloodDonationCentre.blood.model.EBlood;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BloodRepository extends JpaRepository<Blood, Long> {

    List<Blood> findByName(EBlood blood);
}
