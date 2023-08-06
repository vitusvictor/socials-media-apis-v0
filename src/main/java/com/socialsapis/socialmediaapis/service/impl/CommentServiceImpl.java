package com.socialsapis.socialmediaapis.service.impl;

import com.socialsapis.socialmediaapis.entity.Comment;
import com.socialsapis.socialmediaapis.entity.Post;
import com.socialsapis.socialmediaapis.entity.User;
import com.socialsapis.socialmediaapis.exceptions.PostNotFoundException;
import com.socialsapis.socialmediaapis.exceptions.UserNotFoundException;
import com.socialsapis.socialmediaapis.repository.CommentRepo;
import com.socialsapis.socialmediaapis.repository.PostRepo;
import com.socialsapis.socialmediaapis.repository.UserRepo;
import com.socialsapis.socialmediaapis.request.CommentRequest;
import com.socialsapis.socialmediaapis.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {
    private final UserRepo userRepo;

    private final PostRepo postRepo;

    private final CommentRepo commentRepo;
    @Override
    public ResponseEntity<?> commentOnPost(CommentRequest commentRequest) {
        Optional<Post> postOpt = postRepo.findById(commentRequest.getPostId());
        Optional<User> userOpt = userRepo.findById(commentRequest.getCommentatorId());

        if (postOpt.isEmpty()) {
            throw new PostNotFoundException("Post with the id not found.");
        }

        if (userOpt.isEmpty()) {
            throw new UserNotFoundException("User not found.");
        }

        Post post = postOpt.get();
        User user = userOpt.get();
        Comment comment = new Comment();

        comment.setComment(commentRequest.getComment());
        comment.setCommentator(user);
        comment.setPost(post);

        commentRepo.save(comment);

        return new ResponseEntity<>("Comment added.", HttpStatus.CREATED);
    }
}
