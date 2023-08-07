package com.socialsapis.socialmediaapis.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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

import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Disabled;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {PostServiceImpl.class})
@ExtendWith(SpringExtension.class)
class PostServiceImplTest {
    @MockBean
    private PostRepo postRepo;

    @Autowired
    private PostServiceImpl postServiceImpl;

    @MockBean
    private UserRepo userRepo;

    @Test
    void testCreatePost() {
        User user = new User();
        user.setEmail("vitus.vitus@gmail.com");
        ArrayList<User> followers = new ArrayList<>();
        user.setFollowers(followers);
        user.setFollowing(new ArrayList<>());
        user.setId(1L);
        user.setPassword("password");
        user.setPosts(new ArrayList<>());
        user.setProfile("Profile");
        user.setRole("Role");
        user.setUsername("vitusvitus");
        Optional<User> ofResult = Optional.of(user);
        when(userRepo.findById(Mockito.<Long>any())).thenReturn(ofResult);

        Post post = new Post();
        post.setAuthorId(1L);
        post.setComments(new ArrayList<>());
        post.setContent("Not all who wander are lost");
        post.setCreatedDate(LocalDate.of(1970, 1, 1).atStartOfDay());
        post.setId(1L);
        BigInteger likesCount = BigInteger.valueOf(1L);
        post.setLikesCount(likesCount);
        when(postRepo.save(Mockito.<Post>any())).thenReturn(post);

        CreatePostRequest createPostRequest = new CreatePostRequest();
        createPostRequest.setContent("Not all who wander are lost");
        createPostRequest.setUserId(1L);
        ResponseEntity<?> actualCreatePostResult = postServiceImpl.createPost(createPostRequest);
        assertTrue(actualCreatePostResult.hasBody());
        assertEquals(201, actualCreatePostResult.getStatusCodeValue());
        assertTrue(actualCreatePostResult.getHeaders().isEmpty());
        assertEquals(1L, ((PostResponse) actualCreatePostResult.getBody()).getId().longValue());
        assertEquals("Not all who wander are lost", ((PostResponse) actualCreatePostResult.getBody()).getContent());
        assertEquals(followers, ((PostResponse) actualCreatePostResult.getBody()).getComments());
        BigInteger expectedLikesCount = likesCount.ONE;
        BigInteger likesCount2 = ((PostResponse) actualCreatePostResult.getBody()).getLikesCount();
        assertSame(expectedLikesCount, likesCount2);
        assertEquals("00:00", ((PostResponse) actualCreatePostResult.getBody()).getCreatedDate().toLocalTime().toString());
        assertEquals("1", likesCount2.toString());
        verify(userRepo).findById(Mockito.<Long>any());
        verify(postRepo).save(Mockito.<Post>any());
    }
    
    @Test
    void testLikePost() {
        User user = new User();
        user.setEmail("vitus.vitus@gmail.com");
        user.setFollowers(new ArrayList<>());
        user.setFollowing(new ArrayList<>());
        user.setId(1L);
        user.setPassword("password");
        user.setPosts(new ArrayList<>());
        user.setProfile("Profile");
        user.setRole("Role");
        user.setUsername("vitusvitus");
        Optional<User> ofResult = Optional.of(user);
        when(userRepo.findById(Mockito.<Long>any())).thenReturn(ofResult);

        Post post = new Post();
        post.setAuthorId(1L);
        post.setComments(new ArrayList<>());
        post.setContent("Not all who wander are lost");
        post.setCreatedDate(LocalDate.of(1970, 1, 1).atStartOfDay());
        post.setId(1L);
        post.setLikesCount(BigInteger.valueOf(1L));
        Optional<Post> ofResult2 = Optional.of(post);

        Post post2 = new Post();
        post2.setAuthorId(1L);
        post2.setComments(new ArrayList<>());
        post2.setContent("Not all who wander are lost");
        post2.setCreatedDate(LocalDate.of(1970, 1, 1).atStartOfDay());
        post2.setId(1L);
        post2.setLikesCount(BigInteger.valueOf(1L));
        when(postRepo.save(Mockito.<Post>any())).thenReturn(post2);
        when(postRepo.findById(Mockito.<Long>any())).thenReturn(ofResult2);

        LikePostRequest likePostRequest = new LikePostRequest();
        likePostRequest.setPostId(1L);
        likePostRequest.setUserId(1L);
        ResponseEntity<?> actualLikePostResult = postServiceImpl.likePost(likePostRequest);
        assertEquals("Post liked.", actualLikePostResult.getBody());
        assertEquals(201, actualLikePostResult.getStatusCodeValue());
        assertTrue(actualLikePostResult.getHeaders().isEmpty());
        verify(userRepo).findById(Mockito.<Long>any());
        verify(postRepo).save(Mockito.<Post>any());
        verify(postRepo).findById(Mockito.<Long>any());
    }

    @Test
    void testGetPost() {
        when(postRepo.findById(Mockito.<Long>any())).thenReturn(Optional.empty());
        assertThrows(PostNotFoundException.class, () -> postServiceImpl.getPost(1L));
        verify(postRepo).findById(Mockito.<Long>any());
    }

}

