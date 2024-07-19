package com.comment.CommentService.service;

import com.comment.CommentService.payload.CommentDto;
import com.comment.CommentService.responses.PaginationResponse;

public interface CommentService {
     CommentDto saveComment(CommentDto comment, long postId);
        public PaginationResponse<CommentDto> getAllComments(int pageNumber , int pageSize , String sortBy, String sortOrder);
//    public CommentDto getComment(int commentId);
     void deleteComment(long commentId);
//    public List<CommentDto> getCommentsByPostId(int postId);
//    public List<CommentDto> getCommentsByUserId(int userId);

}
