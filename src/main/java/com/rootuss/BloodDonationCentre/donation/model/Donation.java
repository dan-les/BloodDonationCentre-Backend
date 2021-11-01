package com.rootuss.BloodDonationCentre.donation.model;

import com.rootuss.BloodDonationCentre.recipent.model.Recipient;
import com.rootuss.BloodDonationCentre.users.model.User;
import com.sun.istack.Nullable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Data
@Table(name = "donations")
@AllArgsConstructor
@NoArgsConstructor
@Builder
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
    @Nullable
    @ManyToOne(fetch = FetchType.LAZY)
    private Recipient recipient;
}
