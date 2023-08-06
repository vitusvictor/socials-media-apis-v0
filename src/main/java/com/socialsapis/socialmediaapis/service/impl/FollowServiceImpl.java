package com.socialsapis.socialmediaapis.service.impl;

import com.socialsapis.socialmediaapis.entity.User;
import com.socialsapis.socialmediaapis.exceptions.UserNotFoundException;
import com.socialsapis.socialmediaapis.repository.UserRepo;
import com.socialsapis.socialmediaapis.request.FollowRequest;
import com.socialsapis.socialmediaapis.response.UserResponse;
import com.socialsapis.socialmediaapis.service.FollowService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FollowServiceImpl implements FollowService {

    private final UserRepo userRepo;
    @Override
    public ResponseEntity<?> follow(FollowRequest followRequest) {

        Optional<User> followerUser = userRepo.findById(followRequest.getFollowerId());
        Optional<User> followingUser = userRepo.findById(followRequest.getFollowingId()); // person you want to follow

        if (followerUser.isEmpty()) {
            throw new UserNotFoundException("User with follower id not found");
        }
        if (followingUser.isEmpty()) {
            throw new UserNotFoundException("User with following id not found");
        }

        User user = followingUser.get();
        List<User> list = user.getFollowers();
        list.add(followerUser.get());

        user.setFollowers(list);

        userRepo.save(user);

        return new ResponseEntity<>("You now follow this user.", HttpStatus.OK);

    }

    @Override
    public ResponseEntity<?> unfollow(FollowRequest followRequest) {
        Optional<User> followerUser = userRepo.findById(followRequest.getFollowerId());
        Optional<User> followingUser = userRepo.findById(followRequest.getFollowingId()); // person you want to unfollow

        if (followerUser.isEmpty()) {
            throw new UserNotFoundException("User with follower id not found");
        }
        if (followingUser.isEmpty()) {
            throw new UserNotFoundException("User with following id not found");
        }

        User user = followingUser.get();
        List<User> list = user.getFollowers();
        list.remove(followerUser.get());
        user.setFollowers(list);

        userRepo.save(user);

        return new ResponseEntity<>("You've unfollowed this user.", HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> getUser(Long id) {
        Optional<User> user = userRepo.findById(id);

        if (user.isEmpty()) {
            throw new UserNotFoundException("User with not found");
        }

        UserResponse userResponse = new UserResponse();
        userResponse.setUsername(user.get().getUsername());
        userResponse.setId(user.get().getId());
        userResponse.setEmail(user.get().getEmail());

        List<UserResponse> followers = new ArrayList<>();
        List<UserResponse> following = new ArrayList<>();

        for (User user1: user.get().getFollowers()) {
            UserResponse userResponse1 = new UserResponse();
            userResponse1.setId(user1.getId());
            userResponse1.setUsername(user1.getUsername());
            userResponse1.setEmail(user1.getEmail());

            followers.add(userResponse1);
        }

        for (User user1: user.get().getFollowing()) {
            UserResponse userResponse1 = new UserResponse();
            userResponse1.setId(user1.getId());
            userResponse1.setUsername(user1.getUsername());
            userResponse1.setEmail(user1.getEmail());

            following.add(userResponse1);
        }

        userResponse.setFollowers(followers);
        userResponse.setFollowing(following);

        return ResponseEntity.ok(userResponse);
    }

    @Override
    public ResponseEntity<?> getAllUsers() {
        List<UserResponse> list = new ArrayList<>();
        for (User user: userRepo.findAll()) {
            UserResponse userResponse = new UserResponse();
            userResponse.setEmail(user.getEmail());
            userResponse.setId(user.getId());
            userResponse.setUsername(user.getUsername());

            list.add(userResponse);
        }
        return ResponseEntity.ok(list);
    }
}
