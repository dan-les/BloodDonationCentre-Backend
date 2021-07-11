package com.rootuss.BloodDonationCentre.users.repository;


import com.rootuss.BloodDonationCentre.roles.model.ERole;
import com.rootuss.BloodDonationCentre.users.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);

    Boolean existsByUsername(String username);

    Boolean existsByEmail(String email);

    Optional<User> findById(Long id);

    @Query("SELECT distinct u FROM User u JOIN u.roles r WHERE r.name =:roleName")
    List<User> findAllDonors(ERole roleName);

    void deleteById(Long id);
}