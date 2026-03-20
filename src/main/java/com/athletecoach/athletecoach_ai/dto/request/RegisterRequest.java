package com.athletecoach.athletecoach_ai.dto.request;

import com.athletecoach.athletecoach_ai.enums.PlayerRole;
import com.athletecoach.athletecoach_ai.enums.SportName;
import jakarta.validation.constraints.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;


@Data
public class RegisterRequest {
    @NotBlank(message = "Name is Required")
    private String name;

    @Email(message = "Enter valid Email" )
    @NotBlank(message = "Password is Required")
    private String email;

    @Size(min = 8, message = "Password must be atleast 8 characters")
    @NotBlank(message = "Password is Required ")
    private String password;

    @NotNull(message = "Sport is required")
    private SportName sport;

    @NotNull(message = "Role is required")
    private PlayerRole role;

}
