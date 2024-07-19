package com.comment.CommentService.service.impl;

import com.comment.CommentService.entities.Comment;
import com.comment.CommentService.exception.ResourceNotFoundException;
import com.comment.CommentService.payload.CommentDto;
import com.comment.CommentService.payload.PostDto;
import com.comment.CommentService.repositories.CommentRepo;
import com.comment.CommentService.responses.PaginationResponse;
import com.comment.CommentService.service.CommentService;
import com.comment.CommentService.service.PostFeignClient;
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

    @Override
    public CommentDto saveComment(CommentDto comment, long postId) {
        Optional<PostDto> postDto = Optional.ofNullable(postFeignClient.getPostById(postId).getBody());
        Comment commentNew = modelMapper.map(comment, Comment.class);
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
        List<CommentDto> commentDtos = page.getContent().stream().map(comment -> modelMapper.map(comment, CommentDto.class)).collect(Collectors.toList());
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
    public void deleteComment(long commentId) {
        Optional<Comment> comment = Optional.ofNullable(commentRepo.findById(commentId).orElseThrow(() -> new ResourceNotFoundException("Comment", "Id", "" + commentId)));
        if (comment.isPresent()) {
            commentRepo.delete(comment.get());
        }

    }
}
