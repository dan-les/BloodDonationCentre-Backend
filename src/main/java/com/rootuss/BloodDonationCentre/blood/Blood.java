package com.rootuss.BloodDonationCentre.blood;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "bloods")
public class Blood {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private EBlood name;

    public Blood() {

    }

    public Blood(EBlood name) {
        this.name = name;
    }


}