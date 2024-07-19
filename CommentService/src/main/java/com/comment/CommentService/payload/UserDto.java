package com.comment.CommentService.payload;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {

    private Long userId;

   private String name;

    private String email;

    private String password;

   private Long phone;
}
