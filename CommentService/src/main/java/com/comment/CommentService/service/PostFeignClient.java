package com.comment.CommentService.service;

import com.comment.CommentService.payload.PostDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "POSTSERVICE")
public interface PostFeignClient {
    @GetMapping(path = "api/post/{postId}")
    public ResponseEntity<PostDto> getPostById(@PathVariable("postId") Long postId);
}
