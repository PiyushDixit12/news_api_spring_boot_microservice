package com.userService.UserService.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long userId;

    @Column(name = "user_name", length = 50)
    private String name;

    @Column(unique = true)
    private String email;

    @Column(length = 255)
    private String password;

    @Column(length = 10)
    private Long phone;

    @Column(length = 10)
    private String roles;
}
