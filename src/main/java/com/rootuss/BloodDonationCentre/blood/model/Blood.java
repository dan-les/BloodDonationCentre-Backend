package com.rootuss.BloodDonationCentre.blood.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "bloods")
public class Blood {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private EBlood name;

    public Blood(EBlood name) {
        this.name = name;
    }
}