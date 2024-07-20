package com.posts.PostService.entities;

import com.posts.PostService.payload.CommentDto;
import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "posts")
@Data
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long postId;

    private String title;

    private String content;

    private String imageName;

    private Date addedDate;

    private  long userId;

    private long categoryId;

}
