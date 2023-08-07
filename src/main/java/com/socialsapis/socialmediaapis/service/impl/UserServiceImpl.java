package com.socialsapis.socialmediaapis.service.impl;

import com.socialsapis.socialmediaapis.entity.User;
import com.socialsapis.socialmediaapis.exceptions.UserNotFoundException;
import com.socialsapis.socialmediaapis.repository.UserRepo;
import com.socialsapis.socialmediaapis.response.FollowerResponse;
import com.socialsapis.socialmediaapis.response.UserResponse;
import com.socialsapis.socialmediaapis.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;


import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import java.util.*;


@Service
@RequiredArgsConstructor @Slf4j
public class UserServiceImpl implements UserService, UserDetailsService {

    private final UserRepo userRepository;


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<com.socialsapis.socialmediaapis.entity.User> user = userRepository.findByEmail(email);
        SimpleGrantedAuthority authority = new SimpleGrantedAuthority("USER");
        if(user.isEmpty()){
            log.error("User not found");
            throw new UsernameNotFoundException("User not found");
        } else {
            log.info("User Found");

            return new org.springframework.security.core.userdetails.User(user.get().getEmail(), user.get().getPassword(), Collections.singleton(authority));
        }
    }


    @Override
    public ResponseEntity<?> getUser(Long id) {
        Optional<User> user = userRepository.findById(id);

        if (user.isEmpty()) {
            throw new UserNotFoundException("User with this user id not found");
        }

        UserResponse userResponse = new UserResponse();

        userResponse.setUsername(user.get().getUsername());
        userResponse.setId(user.get().getId());
        userResponse.setEmail(user.get().getEmail());

        List<FollowerResponse> followers = new ArrayList<>();
        List<FollowerResponse> following = new ArrayList<>();

        for (User user1: user.get().getFollowers()) {
            FollowerResponse followerResponse = new FollowerResponse();
            followerResponse.setId(user1.getId());
            followerResponse.setUsername(user1.getUsername());
            followerResponse.setEmail(user1.getEmail());

            followers.add(followerResponse);
        }

        for (User user1: user.get().getFollowing()) {
            FollowerResponse followerResponse = new FollowerResponse();
            followerResponse.setId(user1.getId());
            followerResponse.setUsername(user1.getUsername());
            followerResponse.setEmail(user1.getEmail());

            following.add(followerResponse);
        }

        userResponse.setFollowers(followers);
        userResponse.setFollowing(following);

        return ResponseEntity.ok(userResponse);
    }

    @Override
    public ResponseEntity<?> getAllUsers() {
        List<UserResponse> list = new ArrayList<>();
        for (User user: userRepository.findAll()) {
            UserResponse userResponse = new UserResponse();
            userResponse.setEmail(user.getEmail());
            userResponse.setId(user.getId());
            userResponse.setUsername(user.getUsername());

            List<FollowerResponse> followingResponseList = new ArrayList<>();
            for (User user1: user.getFollowing()) {

                FollowerResponse followerResponse = new FollowerResponse();
                followerResponse.setEmail(user1.getEmail());
                followerResponse.setId(user1.getId());
                followerResponse.setUsername(user1.getUsername());

                followingResponseList.add(followerResponse);
            }

            List<FollowerResponse> followerResponseList = new ArrayList<>();


            for (User user1: user.getFollowers()) {
                FollowerResponse followerResponse = new FollowerResponse();
                followerResponse.setEmail(user1.getEmail());
                followerResponse.setId(user1.getId());
                followerResponse.setUsername(user1.getUsername());

                followerResponseList.add(followerResponse);
            }

            userResponse.setFollowing(followingResponseList);
            userResponse.setFollowers(followerResponseList);
            list.add(userResponse);
        }
        return ResponseEntity.ok(list);
    }
}
