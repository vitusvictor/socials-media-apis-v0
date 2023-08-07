package com.socialsapis.socialmediaapis.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.socialsapis.socialmediaapis.entity.User;
import com.socialsapis.socialmediaapis.exceptions.UserNotFoundException;
import com.socialsapis.socialmediaapis.repository.UserRepo;
import com.socialsapis.socialmediaapis.request.FollowRequest;

import java.util.ArrayList;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {FollowServiceImpl.class})
@ExtendWith(SpringExtension.class)
class FollowServiceImplTest {
    @Autowired
    private FollowServiceImpl followServiceImpl;

    @MockBean
    private UserRepo userRepo;


    @Test
    void testFollow() {
        FollowRequest followRequest = new FollowRequest();
        followRequest.setFollowerId(1L);
        followRequest.setFollowingId(1L);
        ResponseEntity<?> actualFollowResult = followServiceImpl.follow(followRequest);
        assertEquals("Oops!, you cannot follow yourself.", actualFollowResult.getBody());
        assertEquals(400, actualFollowResult.getStatusCodeValue());
        assertTrue(actualFollowResult.getHeaders().isEmpty());
    }
    
    @Test
    void testUnfollow() {
        User user = new User();
        user.setEmail("vitus.vitus@gmail.com");
        user.setFollowers(new ArrayList<>());
        user.setFollowing(new ArrayList<>());
        user.setId(1L);
        user.setPassword("passwordyou");
        user.setPosts(new ArrayList<>());
        user.setProfile("Profile");
        user.setRole("Role");
        user.setUsername("vitusvitus");
        Optional<User> ofResult = Optional.of(user);
        when(userRepo.findById(Mockito.<Long>any())).thenReturn(ofResult);

        FollowRequest followRequest = new FollowRequest();
        followRequest.setFollowerId(1L);
        followRequest.setFollowingId(1L);
        ResponseEntity<?> actualUnfollowResult = followServiceImpl.unfollow(followRequest);
        assertEquals("You don't follow this user.", actualUnfollowResult.getBody());
        assertEquals(200, actualUnfollowResult.getStatusCodeValue());
        assertTrue(actualUnfollowResult.getHeaders().isEmpty());
        verify(userRepo, atLeast(1)).findById(Mockito.<Long>any());
    }
}

