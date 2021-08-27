package com.rootuss.BloodDonationCentre.users.roles.repository;


import com.rootuss.BloodDonationCentre.users.roles.model.ERole;
import com.rootuss.BloodDonationCentre.users.roles.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(ERole name);
}
