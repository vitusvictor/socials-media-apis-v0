package com.socialsapis.socialmediaapis.repository;

import com.socialsapis.socialmediaapis.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepo extends JpaRepository<Comment, Long> {
}
