package com.rootuss.BloodDonationCentre.recipent.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "recipents")
public class Recipient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
}
