package com.rootuss.BloodDonationCentre.security.jwt.model;

import com.rootuss.BloodDonationCentre.users.model.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.Instant;

@Getter
@Setter
@NoArgsConstructor
@Entity(name = "refresh_tokens")
public class RefreshToken {
    @Id()
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;
    @Column(nullable = false, unique = true)
    private String token;
    @Column(nullable = false)
    private Instant expiryDate;
}
