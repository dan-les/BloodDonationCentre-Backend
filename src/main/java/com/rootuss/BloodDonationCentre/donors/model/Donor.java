package com.rootuss.BloodDonationCentre.donors.model;

import com.rootuss.BloodDonationCentre.blood.Blood;
import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "donors")
public class Donor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
    private String pesel;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "blood_id")
    private Blood blood;

}
