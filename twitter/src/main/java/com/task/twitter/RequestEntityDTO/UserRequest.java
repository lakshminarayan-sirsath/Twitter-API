package com.task.twitter.RequestEntityDTO;

import java.util.Set;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRequest {

    @NotBlank(message = "Full name cannot be blank.")
    private String fullName;

    @NotBlank(message = "Location cannot be blank.")
    private String location;

    @NotBlank(message = "Birth date cannot be blank.")
    private String birthDate;

    @Email(message = "Email should be valid.")
    @NotBlank(message = "Email cannot be blank.")
    private String email;

    @NotBlank(message = "Password cannot be blank.")
    private String password;

    @NotBlank(message = "Mobile number cannot be blank.")
    @Size(min = 10, max = 10, message = "Mobile number must be 10 digits.")
    private String mobile;
    
    Set<String> roles;

}
