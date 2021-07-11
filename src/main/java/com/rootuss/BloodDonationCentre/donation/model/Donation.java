package com.rootuss.BloodDonationCentre.donation.model;

import com.rootuss.BloodDonationCentre.recipent.model.Recipent;
import com.rootuss.BloodDonationCentre.users.model.User;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Data
@Table(name = "donations")
public class Donation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate date;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;
    private Long amount;

    @Enumerated(EnumType.STRING)
    @Column(name = "donation_type")
    private EDonationType donationType;

    @Column(name = "is_released")
    private Boolean isReleased;

    @OneToOne(fetch = FetchType.LAZY)
    private Recipent recipent;

}
