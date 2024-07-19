package com.posts.PostService.service;

import com.posts.PostService.payload.PostDto;
import com.posts.PostService.responses.PaginationResponse;


public interface PostService {

    PostDto createPost(PostDto postDto, Long userId, Long categoryId);
    PaginationResponse<PostDto> getAllPosts(Integer pageNumber, Integer pageSize, String sortBy, String sortOrder);
    PostDto getPostById(long id);
    PostDto updatePost( PostDto postDto ,long postId);
    void deletePost(long id);
}
