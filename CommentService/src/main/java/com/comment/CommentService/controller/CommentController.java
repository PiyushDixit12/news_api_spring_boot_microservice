package com.comment.CommentService.controller;

import com.comment.CommentService.payload.CommentDto;
import com.comment.CommentService.responses.DeleteApiResponse;
import com.comment.CommentService.responses.PaginationResponse;
import com.comment.CommentService.service.CommentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/comment")
@CrossOrigin(origins = "*")
@Tag(name = "Comment Controller", description = "Controller for managing comments")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @Operation(summary = "Post a comment")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Comment created successfully", content = @Content(mediaType = "application/json", schema = @Schema(implementation = CommentDto.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input", content = @Content),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
            @ApiResponse(responseCode = "404", description = "Post or User not found", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
    })
    @PostMapping("/post/{postId}/user/{userId}")
    public ResponseEntity<CommentDto> postComment(@RequestBody CommentDto commentDto, @PathVariable long postId, @PathVariable long userId) {
        return new ResponseEntity<>(commentService.saveComment(commentDto, postId, userId), HttpStatus.CREATED);
    }

    @Operation(summary = "Get all comments with pagination")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved comments", content = @Content(mediaType = "application/json", schema = @Schema(implementation = PaginationResponse.class))),
            @ApiResponse(responseCode = "400", description = "Invalid parameters", content = @Content)
    })
    @GetMapping
    public ResponseEntity<PaginationResponse<CommentDto>> getAllComments(@RequestParam(name = "pageNumber", defaultValue = "0", required = false) Integer pageNumber,
                                                                         @RequestParam(name = "pageSize", defaultValue = "10", required = false) Integer pageSize,
                                                                         @RequestParam(name = "sortBy", defaultValue = "commentId", required = false) String sortBy,
                                                                         @RequestParam(name = "sortOrder", defaultValue = "asc", required = false) String sortOrder
    ) {
        return new ResponseEntity<>(commentService.getAllComments(pageNumber, pageSize, sortBy, sortOrder), HttpStatus.OK);
    }

    @Operation(summary = "Get comments by user ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved comments", content = @Content(mediaType = "application/json", schema = @Schema(implementation = CommentDto.class))),
            @ApiResponse(responseCode = "404", description = "User not found", content = @Content)
    })
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<CommentDto>> getCommentsByUserId(@PathVariable long userId) {
        return new ResponseEntity<>(commentService.getCommentsByUserId(userId), HttpStatus.OK);
    }

    @Operation(summary = "Get comments by post ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved comments", content = @Content(mediaType = "application/json", schema = @Schema(implementation = CommentDto.class))),
            @ApiResponse(responseCode = "404", description = "Post not found", content = @Content)
    })
    @GetMapping("/post/{postId}")
    public ResponseEntity<List<CommentDto>> getCommentsByPostId(@PathVariable long postId) {
        return new ResponseEntity<>(commentService.getCommentsByPostId(postId), HttpStatus.OK);
    }

    @Operation(summary = "Delete a comment")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Comment deleted successfully", content = @Content(mediaType = "application/json", schema = @Schema(implementation = DeleteApiResponse.class))),
            @ApiResponse(responseCode = "404", description = "Comment not found", content = @Content)
    })
    @DeleteMapping("/{commentId}")
    public ResponseEntity<DeleteApiResponse> deleteComment(@PathVariable long commentId) {
        commentService.deleteComment(commentId);
        return new ResponseEntity<>(new DeleteApiResponse("Comment Deleted successfully", true), HttpStatus.OK);
    }


}
