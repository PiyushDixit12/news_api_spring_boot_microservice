package com.comment.CommentService.repositories;

import com.comment.CommentService.entities.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepo extends JpaRepository<Comment,Long> {
}
