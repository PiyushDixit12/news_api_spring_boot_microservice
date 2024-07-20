package com.posts.PostService.service;

import com.posts.PostService.payload.CommentDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "COMMENTSERVICE")
public interface CommentFeignClient {
    @GetMapping("/api/comment/user/{userId}")
     ResponseEntity<List<CommentDto>> getCommentsByUserId(@PathVariable long userId);

    @GetMapping("/api/comment/post/{postId}")
     ResponseEntity<List<CommentDto>> getCommentsByPostId(@PathVariable long postId);

}
