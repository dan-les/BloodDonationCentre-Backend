package com.rootuss.BloodDonationCentre.users.model;

import com.rootuss.BloodDonationCentre.blood.model.Blood;
import com.rootuss.BloodDonationCentre.users.account.UserDetailsImpl;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@Entity
@Table(name = "users",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "email")
        })

public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank
    @Size(max = 50)
    @Email
    private String email;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
    @Size(max = 11)
    private String pesel;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "blood_id")
    private Blood blood;
    private String gender;
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    private UserDetailsImpl userDetails;
}