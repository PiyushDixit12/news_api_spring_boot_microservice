package com.comment.CommentService.controller;

import com.comment.CommentService.payload.CommentDto;
import com.comment.CommentService.responses.DeleteApiResponse;
import com.comment.CommentService.responses.PaginationResponse;
import com.comment.CommentService.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/comment")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @PostMapping("/post/{postId}/user/{userId}")
    public ResponseEntity<CommentDto> postComment(@RequestBody CommentDto commentDto, @PathVariable long postId, @PathVariable long userId) {
        return new ResponseEntity<>(commentService.saveComment(commentDto, postId, userId), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<PaginationResponse<CommentDto>> getAllComments(@RequestParam(name = "pageNumber", defaultValue = "0", required = false) Integer pageNumber,
                                                                         @RequestParam(name = "pageSize", defaultValue = "10", required = false) Integer pageSize,
                                                                         @RequestParam(name = "sortBy", defaultValue = "commentId", required = false) String sortBy,
                                                                         @RequestParam(name = "sortOrder", defaultValue = "asc", required = false) String sortOrder
    ) {
        return new ResponseEntity<>(commentService.getAllComments(pageNumber, pageSize, sortBy, sortOrder), HttpStatus.OK);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<CommentDto>> getCommentsByUserId(@PathVariable long userId) {
        return new ResponseEntity<>(commentService.getCommentsByUserId(userId), HttpStatus.OK);
    }

    @GetMapping("/post/{postId}")
    public ResponseEntity<List<CommentDto>> getCommentsByPostId(@PathVariable long postId) {
        return new ResponseEntity<>(commentService.getCommentsByPostId(postId), HttpStatus.OK);
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<DeleteApiResponse> deleteComment(@PathVariable long commentId) {
        commentService.deleteComment(commentId);
        return new ResponseEntity<>(new DeleteApiResponse("Comment Deleted successfully", true), HttpStatus.OK);
    }


}
