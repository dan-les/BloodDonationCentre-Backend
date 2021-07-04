package com.rootuss.BloodDonationCentre.repository;


import com.rootuss.BloodDonationCentre.models.ERole;
import com.rootuss.BloodDonationCentre.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(ERole name);
}
