package com.bobby.onlinestore.Dtos;

import com.bobby.onlinestore.Validation.LowerCase;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class RegisterUserRequest {
    @NotBlank(message = "Name is required")
    @Size(max = 255, message = "Name must be less than 255 character")
    private String name;

    @NotBlank(message = "This field is required")
    @Email(message = "Email must be valid")
    @LowerCase(message = "Email must be in Lowercase")
    private String email;

    @NotBlank(message = "Please provide password here")
    @Size(min = 6, max = 25, message = "Password should be min 6 character in length and less than 25")
    private String password;
}
