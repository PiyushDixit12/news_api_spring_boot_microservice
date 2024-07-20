package com.comment.CommentService.entities;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "comments")
@Data
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int commentId;

    private String content;

    private long postId;

    private long userId;

//    @ManyToOne
//    @JoinColumn(name = "post_id")
//    private Post post;

//    @ManyToOne
//    private User user;

}