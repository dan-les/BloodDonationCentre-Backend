package com.rootuss.BloodDonationCentre.reservation.model;

import com.rootuss.BloodDonationCentre.donation.model.EDonationType;
import com.rootuss.BloodDonationCentre.users.model.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "reservations")
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;
    private LocalDate date;
    private LocalTime time;
    @Enumerated(EnumType.STRING)
    @Column(name = "donation_type")
    private EDonationType donationType;
    @Column(name = "creation_date")
    private LocalDate creationDate;
}
