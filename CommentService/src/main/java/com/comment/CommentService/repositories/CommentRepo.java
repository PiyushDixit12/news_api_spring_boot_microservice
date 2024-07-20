package com.comment.CommentService.repositories;

import com.comment.CommentService.entities.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepo extends JpaRepository<Comment,Long> {

    List<Comment> findByPostId(long id);
    List<Comment> findByUserId(long id);
}
