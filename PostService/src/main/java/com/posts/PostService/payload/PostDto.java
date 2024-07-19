package com.posts.PostService.payload;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostDto {

        private long postId;

        private String title;

        private String content;

        private String imageName;

        private Date addedDate;
}
