package com.socialsapis.socialmediaapis.service;

import com.socialsapis.socialmediaapis.request.FollowRequest;
import org.springframework.http.ResponseEntity;

public interface FollowService {
    ResponseEntity<?> follow(FollowRequest followRequest);

    ResponseEntity<?> unfollow(FollowRequest followRequest);

//    ResponseEntity<?> getUser();

//    ResponseEntity<?> getUser(Long id);
//
//    ResponseEntity<?> getAllUsers();
}
