package com.comment.CommentService.service.impl;

import com.comment.CommentService.entities.Comment;
import com.comment.CommentService.exception.ResourceNotFoundException;
import com.comment.CommentService.payload.CommentDto;
import com.comment.CommentService.payload.PostDto;
import com.comment.CommentService.payload.UserDto;
import com.comment.CommentService.repositories.CommentRepo;
import com.comment.CommentService.responses.PaginationResponse;
import com.comment.CommentService.service.CommentService;
import com.comment.CommentService.service.PostFeignClient;
import com.comment.CommentService.service.UserFeignClient;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    CommentRepo commentRepo;

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    PostFeignClient postFeignClient;
    @Autowired
    UserFeignClient userFeignClient;

    @Override
    public CommentDto saveComment(CommentDto comment, long postId,long userId) {

        Optional<PostDto> postDto = Optional.ofNullable(postFeignClient.getPostById(postId).getBody());
        Optional<UserDto> uerDto = Optional.ofNullable(userFeignClient.getUser(userId).getBody());

        Comment commentNew = modelMapper.map(comment, Comment.class);
        commentNew.setPostId(postDto.get().getPostId());
        commentNew.setUserId(uerDto.get().getUserId());
        return modelMapper.map(commentRepo.save(commentNew), CommentDto.class);
    }

    @Override
    public PaginationResponse<CommentDto> getAllComments(int pageNumber, int pageSize, String sortBy, String sortOrder) {
        Pageable p = null;
        if (sortOrder.equalsIgnoreCase("asc")) {
            p = (Pageable) PageRequest.of(pageNumber, pageSize, Sort.by(sortBy).ascending());
        } else {
            p = (Pageable) PageRequest.of(pageNumber, pageSize, Sort.by(sortBy).descending());
        }
        Page<Comment> page = commentRepo.findAll(p);

        List<CommentDto> commentDtos = page.getContent().stream().map(comment -> {

         CommentDto commentDto=   modelMapper.map(comment, CommentDto.class);
         commentDto.setUser(userFeignClient.getUser(comment.getUserId()).getBody());
         return commentDto;
        }).collect(Collectors.toList());
        PaginationResponse<CommentDto> commentDtoPaginationResponse = new PaginationResponse<CommentDto>(
                page.getNumber(),
                page.getSize(),
                page.getNumberOfElements(),
                page.getTotalPages(),
                page.hasNext(),
                commentDtos
        );

        return commentDtoPaginationResponse;
    }

    @Override
    public CommentDto getComment(long commentId) {
        Optional<Comment> comment = Optional.ofNullable(commentRepo.findById(commentId).orElseThrow(() -> new ResourceNotFoundException("Comment", "Id", "" + commentId)));

        CommentDto commentDto= modelMapper.map(comment.get(), CommentDto.class);
        commentDto.setUser(userFeignClient.getUser(comment.get().getUserId()).getBody());
        return commentDto;
    }

    @Override
    public List<CommentDto> getCommentsByPostId(long postId) {

        return commentRepo.findByPostId(postId).stream().map((comment) -> {
         CommentDto commentDto=   modelMapper.map(comment, CommentDto.class);
         commentDto.setUser(userFeignClient.getUser(comment.getUserId()).getBody());
         return commentDto;
        }).collect(Collectors.toList());
    }

    @Override
    public List<CommentDto> getCommentsByUserId(long userId) {

        return commentRepo.findByUserId(userId).stream().map((comment) -> {

            CommentDto commentDto=   modelMapper.map(comment, CommentDto.class);
            commentDto.setUser(userFeignClient.getUser(comment.getUserId()).getBody());
            return commentDto;
        }).collect(Collectors.toList());
    }

    @Override
    public void deleteComment(long commentId) {
        Optional<Comment> comment = Optional.ofNullable(commentRepo.findById(commentId).orElseThrow(() -> new ResourceNotFoundException("Comment", "Id", "" + commentId)));
        if (comment.isPresent()) {
            commentRepo.delete(comment.get());
        }

    }
}
