package com.posts.PostService.service.impl;

import com.posts.PostService.entities.Post;
import com.posts.PostService.exception.ResourceNotFoundException;
import com.posts.PostService.payload.CategoryDto;
import com.posts.PostService.payload.PostDto;
import com.posts.PostService.payload.UserDto;
import com.posts.PostService.repositories.PostRepo;
import com.posts.PostService.responses.PaginationResponse;
import com.posts.PostService.service.CategoryFeignClient;
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
    private CategoryFeignClient categoryFeignClient;

    @Override
    public PostDto createPost(PostDto postDto, Long userId, Long categoryId) {

        Optional<UserDto> userOptional = Optional.ofNullable(userFeignClient.getUser(userId).getBody());
        Optional<CategoryDto> categoryOptional = Optional.ofNullable(categoryFeignClient.getCategory(categoryId).getBody());

        if (userOptional.isPresent() && categoryOptional.isPresent()) {

            Post post = modelMapper.map(postDto, Post.class);
            post.setImageName("default.png");
            post.setAddedDate(new Date());
//            post.setUser(userOptional.get());
//            post.setCategory(categoryOptional.get());
            return modelMapper.map(postRepo.save(post), PostDto.class);
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

        PaginationResponse<PostDto> paginationResponse = new PaginationResponse<>(
                posts.getNumber(),
                posts.getSize(),
                posts.getNumberOfElements(),
                posts.getTotalPages(),
                posts.hasNext(),
                posts.getContent().stream().map((post -> modelMapper.map(post, PostDto.class))).toList()
        );

        return paginationResponse;
    }

    @Override
    public PostDto getPostById(long id) {
        Optional<Post> postOptional = Optional.ofNullable(postRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post", "Id", ""+id)));
        return modelMapper.map(postOptional.get(), PostDto.class);
    }

    @Override
    public PostDto updatePost(PostDto postDto, long postId) {
        Optional<Post> post = Optional.ofNullable(postRepo.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post", "Id", ""+postId)));
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
            return modelMapper.map(postRepo.save(updatedPost), PostDto.class);
        }

        return null;
    }

    @Override
    public void deletePost(long id) {
        Optional<Post> post = Optional.ofNullable(postRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post", "Id", ""+id)));
        postRepo.delete(post.get());
    }
}
