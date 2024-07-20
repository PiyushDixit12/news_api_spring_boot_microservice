package com.posts.PostService.service.impl;

import com.posts.PostService.entities.Post;
import com.posts.PostService.exception.ResourceNotFoundException;
import com.posts.PostService.payload.CategoryDto;
import com.posts.PostService.payload.PostDto;
import com.posts.PostService.payload.UserDto;
import com.posts.PostService.repositories.PostRepo;
import com.posts.PostService.responses.PaginationResponse;
import com.posts.PostService.service.CategoryFeignClient;
import com.posts.PostService.service.CommentFeignClient;
import com.posts.PostService.service.PostService;
import com.posts.PostService.service.UserFeignClient;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
@NoArgsConstructor
public class PostServiceImpl implements PostService {
    @Autowired
    private PostRepo postRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private UserFeignClient userFeignClient;

    @Autowired
    private CommentFeignClient commentFeignClient;

    @Autowired
    private CategoryFeignClient categoryFeignClient;

    @Override
    public PostDto createPost(PostDto postDto, Long userId, Long categoryId) {

        Optional<UserDto> userOptional = Optional.ofNullable(userFeignClient.getUser(userId).getBody());
        Optional<CategoryDto> categoryOptional = Optional.ofNullable(categoryFeignClient.getCategory(categoryId).getBody());

        if (userOptional.isPresent() && categoryOptional.isPresent()) {

            Post post = modelMapper.map(postDto, Post.class);
            post.setImageName("default.png");
            post.setAddedDate(new Date());
            post.setUserId(userOptional.get().getUserId());
            post.setCategoryId(categoryOptional.get().getCategoryId());
            PostDto savedPostDto= modelMapper.map(postRepo.save(post), PostDto.class);
            savedPostDto.setUser(userOptional.get());
        }
        return null;
    }

    @Override
    public PaginationResponse<PostDto> getAllPosts(Integer pageNumber, Integer pageSize, String sortBy, String sortOrder) {

        Pageable p;
        if (sortOrder.equalsIgnoreCase("asc")) {
            p = PageRequest.of(pageNumber, pageSize, Sort.by(sortBy).ascending());
        } else {
            p = PageRequest.of(pageNumber, pageSize, Sort.by(sortBy).descending());
        }

        Page<Post> posts = postRepo.findAll(p);
        List<PostDto> postDtos = posts.getContent().stream().map((post -> {

            PostDto postDto = modelMapper.map(post, PostDto.class);
            postDto.setComments(commentFeignClient.getCommentsByPostId(postDto.getPostId()).getBody());
            postDto.setUser(userFeignClient.getUser(post.getUserId()).getBody());
            return postDto;

        })).toList();


        PaginationResponse<PostDto> paginationResponse = new PaginationResponse<>(
                posts.getNumber(),
                posts.getSize(),
                posts.getNumberOfElements(),
                posts.getTotalPages(),
                posts.hasNext(),
                postDtos
                );

        return paginationResponse;
    }

    @Override
    public PostDto getPostById(long id) {
        Optional<Post> postOptional = Optional.ofNullable(postRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post", "Id", "" + id)));
        PostDto postDto = modelMapper.map(postOptional.get(), PostDto.class);
        postDto.setUser(userFeignClient.getUser(postOptional.get().getUserId()).getBody());
        postDto.setComments(commentFeignClient.getCommentsByPostId(postDto.getPostId()).getBody());
        return postDto;
    }

    @Override
    public PostDto updatePost(PostDto postDto, long postId) {
        Optional<Post> post = Optional.ofNullable(postRepo.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post", "Id", "" + postId)));
        if (post.isPresent()) {
            Post updatedPost = modelMapper.map(post.get(), Post.class);
            if (postDto.getImageName() != null && !postDto.getImageName().trim().isEmpty()) {
                updatedPost.setImageName(postDto.getImageName());
            }
            if (postDto.getTitle() != null && !postDto.getTitle().trim().isEmpty()) {
                updatedPost.setTitle(postDto.getTitle());
            }
            if (postDto.getContent() != null && !postDto.getContent().trim().isEmpty()) {
                updatedPost.setContent(postDto.getContent());
            }
            PostDto savePostDto= modelMapper.map(postRepo.save(updatedPost), PostDto.class);
            savePostDto.setUser(userFeignClient.getUser(post.get().getUserId()).getBody());
            savePostDto.setComments(commentFeignClient.getCommentsByPostId(savePostDto.getPostId()).getBody());
            return savePostDto;
        }

        return null;
    }

    @Override
    public void deletePost(long id) {
        Optional<Post> post = Optional.ofNullable(postRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post", "Id", "" + id)));
        postRepo.delete(post.get());
    }
}
