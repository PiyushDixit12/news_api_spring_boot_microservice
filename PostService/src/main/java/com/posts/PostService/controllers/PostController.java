package com.posts.PostService.controllers;

import com.posts.PostService.entities.Post;
import com.posts.PostService.payload.PostDto;
import com.posts.PostService.responses.DeleteApiResponse;
import com.posts.PostService.responses.PaginationResponse;
import com.posts.PostService.service.PostService;
import com.posts.PostService.utils.ImageUpload;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;

@RestController
@RequestMapping(path = "/api/post")
public class PostController {

    @Autowired
    private PostService postService;

    @Value("${project.image}")
    private String path;

    @Autowired
    private ImageUpload uploadFile;

    @PostMapping(path = "/user/{userId}/category/{categoryId}")
    public ResponseEntity<PostDto> createPost(
            @RequestBody PostDto post,
            @PathVariable("userId") Long userId,
            @PathVariable("categoryId") Long categoryId) {
        return new ResponseEntity(postService.createPost(post, userId, categoryId),
                HttpStatus.CREATED);
    }
@GetMapping
    public ResponseEntity<PaginationResponse<PostDto>> getPosts(@RequestParam(name = "pageNumber", defaultValue = "0", required = false) int pageNumber,
                                                                @RequestParam(name = "pageSize", defaultValue = "10", required = false) int pageSize,
                                                                @RequestParam(name = "sortBy",defaultValue = "postId", required = false) String sortBy,
                                                                @RequestParam(name = "sortOrder", defaultValue = "asc", required = false) String sortOrder
    ) {
        return new ResponseEntity<>(postService.getAllPosts(pageNumber, pageSize, sortBy, sortOrder), HttpStatus.OK);
    }

    @GetMapping(path = "/{postId}")
    public ResponseEntity<PostDto> getPostById(@PathVariable("postId") Long postId) {
        return new ResponseEntity<>(postService.getPostById(postId), HttpStatus.OK);
    }
    @PutMapping("/{postId}")
    public ResponseEntity<PostDto> updatePost(@RequestBody PostDto post, @PathVariable("postId") Long postId) {
        return new ResponseEntity<>(postService.updatePost(post, postId), HttpStatus.OK);
    }

    @DeleteMapping("/{postId}")
    public ResponseEntity<DeleteApiResponse> deletePost(@PathVariable("postId") Long postId) {
        postService.deletePost(postId);
        return new ResponseEntity<>(new DeleteApiResponse("Post Deleted Successfully with id: "+postId,true), HttpStatus.OK);
    }



//    Image Operation
    @PostMapping("/image/upload/{postId}")
    public ResponseEntity<String> uploadFileImage(@RequestParam("image") MultipartFile file, @PathVariable int postId) throws IOException {

        Optional<PostDto> postOptional = Optional.ofNullable(postService.getPostById(postId));
        String fileName = uploadFile.uploadFile(path, file);
        if (postOptional.isPresent()) {
            postOptional.get().setImageName(fileName);
            postService.updatePost(postOptional.get(), postId);
        }
        return new ResponseEntity<>("Image Uploaded Successfully on path " + fileName, HttpStatus.OK);
    }

    @GetMapping(path = "/image/{postId}", produces = MediaType.IMAGE_JPEG_VALUE)
    public void getFileImage(@PathVariable("postId") Long postId, HttpServletResponse response) throws IOException {
        PostDto postDto = postService.getPostById(postId);
        InputStream io = uploadFile.getResource(path, postDto.getImageName());

        response.setContentType(MediaType.IMAGE_JPEG_VALUE);
        StreamUtils.copy(io, response.getOutputStream());
    }
}