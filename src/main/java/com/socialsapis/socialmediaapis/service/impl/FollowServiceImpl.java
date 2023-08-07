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
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FollowServiceImpl implements FollowService {

    private final UserRepo userRepo;
    @Override
    public ResponseEntity<?> follow(FollowRequest followRequest) {

        if (followRequest.getFollowingId().equals(followRequest.getFollowerId())) {
            return new ResponseEntity<>("Oops!, you cannot follow yourself.", HttpStatus.BAD_REQUEST);
        }

        Optional<User> followerUser = userRepo.findById(followRequest.getFollowerId());
        Optional<User> followingUser = userRepo.findById(followRequest.getFollowingId()); // person you want to follow

        if (followerUser.isEmpty()) {
            throw new UserNotFoundException("User with follower id not found");
        }
        if (followingUser.isEmpty()) {
            throw new UserNotFoundException("User with following id not found");
        }

        for (User user: followerUser.get().getFollowing()) { // those you are following
            if (Objects.equals(user.getId(), followRequest.getFollowingId())) {
                return new ResponseEntity<>("You already follow this user.", HttpStatus.OK);
            }
        }

        User user = followingUser.get();
        List<User> list = user.getFollowers();
        list.add(followerUser.get());

        user.setFollowers(list);

        User user1 = followerUser.get();
        List<User> list2 = user1.getFollowing();
        list2.add(followingUser.get());

        user1.setFollowing(list2);

        userRepo.save(user);
        userRepo.save(user1);

        return new ResponseEntity<>("You now follow this user.", HttpStatus.OK);

    }

    @Override
    public ResponseEntity<?> unfollow(FollowRequest followRequest) {
        Optional<User> userUnfollowing = userRepo.findById(followRequest.getFollowerId());
        Optional<User> userToUnfollow = userRepo.findById(followRequest.getFollowingId()); // person you want to unfollow

        if (userUnfollowing.isEmpty()) {
            throw new UserNotFoundException("User with follower id not found");
        }
        if (userToUnfollow.isEmpty()) {
            throw new UserNotFoundException("User with following id not found");
        }
        boolean found = false;

        for (User user: userUnfollowing.get().getFollowing()) { // those you are following
            if (Objects.equals(user.getId(), followRequest.getFollowingId())) {
                found = true;
                break;
            }
        }

        if (!found) {
            return new ResponseEntity<>("You don't follow this user.", HttpStatus.OK);
        }

        // removing the user from the follower list
        User user = userToUnfollow.get();
        List<User> list = user.getFollowers();
        list.remove(userUnfollowing.get());
        user.setFollowers(list);

        // removing the user from the following list
        User user1 = userUnfollowing.get();
        List<User> list1 = user.getFollowing();
        list1.remove(userToUnfollow.get());
        user.setFollowing(list);

        userRepo.save(user);
        userRepo.save(user1);

        return new ResponseEntity<>("You've unfollowed this user.", HttpStatus.OK);
    }


}
