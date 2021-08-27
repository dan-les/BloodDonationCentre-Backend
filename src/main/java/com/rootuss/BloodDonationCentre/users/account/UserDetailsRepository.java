package com.rootuss.BloodDonationCentre.users.account;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserDetailsRepository extends JpaRepository<UserDetailsImpl, Long> {
    Optional<UserDetailsImpl> findByUsername(String username);

    Boolean existsByUsername(String username);
}
