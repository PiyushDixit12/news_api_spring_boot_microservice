package com.userService.UserService.payload;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.NumberFormat;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {

    private Long userId;

    @NotNull(message = "please provide user name")
    @Size(min = 3, max = 50, message = "user name must be in min 3 and max 50 characters")
    private String name;

    @NotNull(message = "please provide email")
    @Email(message = "please provide a valid email")
    private String email;

    @NotNull(message = "please provide password")
    @Size(min = 8, max = 8, message = "password must be only 8 characters")
    private String password;

    @NotNull(message = "please provide phone")
    @NumberFormat
    private Long phone;

    @NotNull(message = "please provide role")
    private String roles;


}
