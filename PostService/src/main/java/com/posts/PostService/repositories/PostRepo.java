package com.posts.PostService.repositories;

import com.posts.PostService.entities.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepo extends JpaRepository<Post,Long> {

}
