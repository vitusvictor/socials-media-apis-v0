package com.socialsapis.socialmediaapis.repository;

import com.socialsapis.socialmediaapis.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepo extends JpaRepository<Post, Long> {
}
