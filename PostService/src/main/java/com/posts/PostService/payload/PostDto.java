package com.posts.PostService.payload;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostDto {

    private long postId;

    private String title;

    private String content;

    private String imageName;

    private Date addedDate;

    private UserDto user;

    private List<CommentDto> comments = new ArrayList<>();
}
