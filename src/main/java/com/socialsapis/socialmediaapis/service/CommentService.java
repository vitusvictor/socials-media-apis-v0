package com.socialsapis.socialmediaapis.service;

import com.socialsapis.socialmediaapis.entity.Comment;
import com.socialsapis.socialmediaapis.request.CommentRequest;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface CommentService {

    ResponseEntity<?> commentOnPost(CommentRequest commentRequest);

}
