package com.userService.UserService.payload;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthRequest {

    @NotNull(message = "please provide user name")
    @Size(min = 3, max = 50, message = "user name must min 3 and max 50 characters")
    private String userName;

    @NotNull(message = "please provide password")
    @Size(min = 8, max = 8, message = "password must be only 8 characters")
    private String password;

    @Email(message = "please provide valid email")
    private String email;

   @NotNull(message = "please provide roles")
    private String roles;
}

