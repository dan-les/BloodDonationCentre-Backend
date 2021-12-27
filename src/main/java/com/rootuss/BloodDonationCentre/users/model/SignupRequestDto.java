package com.rootuss.BloodDonationCentre.users.model;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Set;

@Getter
@Setter
public class SignupRequestDto {
    @NotNull(message = "Username can not be blank")
    @Size(min = 3, max = 20)
    private String username;
    @NotNull(message = "Email can not be blank")
    @Size(max = 50)
    @Email
    private String email;
    private Set<String> role;
    @NotNull(message = "Password can not be blank")
    @Size(min = 6, max = 40)
    private String password;
    @NotNull(message = "FirstName can not be blank")
    @Size(min = 1, max = 40)
    private String firstName;
    @NotNull(message = "LastName can not be blank")
    @Size(min = 1, max = 40)
    private String lastName;
}
