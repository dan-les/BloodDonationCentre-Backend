package com.rootuss.BloodDonationCentre.users.repository;


import com.rootuss.BloodDonationCentre.users.model.User;
import com.rootuss.BloodDonationCentre.users.roles.model.ERole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Boolean existsByEmail(String email);

    Optional<User> findById(Long id);

    @Query("SELECT distinct u FROM User u JOIN u.userDetails ud JOIN ud.roles r WHERE r.name =:roleName")
    List<User> findAllDonors(ERole roleName);

    void deleteById(Long id);
}
