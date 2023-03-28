package br.com.webfluxcourse.entity.model.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UserRequest(

        @Size(min = 3, max = 50, message = "Must Be Between 3 and 50 characters")
        @NotBlank(message = "Must Not Be Null Empty")
        String name,
        @Email(message = "Invalid Email")
        @NotBlank(message = "Must Not Be Null Empty")
        String email,
        @Size(min = 3, max = 50, message = "Must Be Between 3 and 50 characters")
        @NotBlank(message = "Must Not Be Null Empty")
        String password
) {
}

