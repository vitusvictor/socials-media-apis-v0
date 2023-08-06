package com.socialsapis.socialmediaapis.service;

import com.socialsapis.socialmediaapis.entity.Post;
import com.socialsapis.socialmediaapis.request.CreatePostRequest;
import com.socialsapis.socialmediaapis.request.LikePostRequest;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface PostService {

    ResponseEntity<?> createPost(CreatePostRequest createPostRequest);

//    List<Post> getAllPost();

    ResponseEntity<?> likePost(LikePostRequest likePostRequest);

    ResponseEntity<?> getPost(Long id);
}
