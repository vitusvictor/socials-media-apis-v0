package com.socialsapis.socialmediaapis.service.impl;

import com.socialsapis.socialmediaapis.entity.Comment;
import com.socialsapis.socialmediaapis.entity.Post;
import com.socialsapis.socialmediaapis.entity.User;
import com.socialsapis.socialmediaapis.exceptions.PostNotFoundException;
import com.socialsapis.socialmediaapis.exceptions.UserNotFoundException;
import com.socialsapis.socialmediaapis.repository.PostRepo;
import com.socialsapis.socialmediaapis.repository.UserRepo;
import com.socialsapis.socialmediaapis.request.CreatePostRequest;
import com.socialsapis.socialmediaapis.request.LikePostRequest;
import com.socialsapis.socialmediaapis.response.PostResponse;
import com.socialsapis.socialmediaapis.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {
    private final UserRepo userRepo;

    private final PostRepo postRepo;

    @Override
    public ResponseEntity<?> createPost(CreatePostRequest createPostRequest) {
        Optional<User> user = userRepo.findById(createPostRequest.getUserId());

        if (user.isEmpty()) {
            throw new UserNotFoundException("User with user id not found");
        }

        Post post = new Post();
        post.setAuthorId(user.get().getId());
        post.setCreatedDate(LocalDateTime.now());
        post.setComments(new ArrayList<>());
        post.setContent(createPostRequest.getContent());
        post.setLikesCount(BigInteger.valueOf(0));
        Post post1 = postRepo.save(post);

        PostResponse postResponse = new PostResponse();
        postResponse.setCreatedDate(post1.getCreatedDate());
        postResponse.setId(post1.getId());
        postResponse.setContent(post1.getContent());
        postResponse.setComments(post1.getComments());
        postResponse.setLikesCount(post1.getLikesCount());

        return new ResponseEntity<>(postResponse, HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<?> likePost(LikePostRequest likePostRequest) {
        Optional<Post> postOpt = postRepo.findById(likePostRequest.getPostId());
        Optional<User> userOpt = userRepo.findById(likePostRequest.getUserId());

        if (postOpt.isEmpty()) {
            throw new PostNotFoundException("Post with the id not found.");
        }

        if (userOpt.isEmpty()) {
            throw new UserNotFoundException("User not found.");
        }
        Post post = postOpt.get();
        post.setLikesCount(post.getLikesCount().add(BigInteger.valueOf(1)));
        postRepo.save(post);

        return new ResponseEntity<>("Post liked.", HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<?> getPost(Long id) {
        Optional<Post> postOpt = postRepo.findById(id);

        if (postOpt.isEmpty()) {
            throw new PostNotFoundException("Post with the id not found.");
        }

        PostResponse postResponse = new PostResponse();
        postResponse.setComments(postOpt.get().getComments());
        postResponse.setContent(postOpt.get().getContent());
        postResponse.setLikesCount(postOpt.get().getLikesCount());
        postResponse.setId(postOpt.get().getId());
        postResponse.setCreatedDate(postOpt.get().getCreatedDate());

        return ResponseEntity.ok(postResponse);
    }


}
