package com.rootuss.BloodDonationCentre.blood;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BloodRepository extends JpaRepository<Blood, Long> {

    List<Blood> findByName(EBlood blood);
}
