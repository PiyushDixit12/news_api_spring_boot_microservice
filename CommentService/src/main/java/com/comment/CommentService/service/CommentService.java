package com.comment.CommentService.service;

import com.comment.CommentService.payload.CommentDto;
import com.comment.CommentService.responses.PaginationResponse;

import java.util.List;

public interface CommentService {
     CommentDto saveComment(CommentDto comment, long postId,long userId);
        public PaginationResponse<CommentDto> getAllComments(int pageNumber , int pageSize , String sortBy, String sortOrder);
    public CommentDto getComment(long commentId);
     void deleteComment(long commentId);
    public List<CommentDto> getCommentsByPostId(long postId);
    public List<CommentDto> getCommentsByUserId(long userId);

}
