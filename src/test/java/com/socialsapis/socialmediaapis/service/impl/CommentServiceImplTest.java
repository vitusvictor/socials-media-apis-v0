package com.socialsapis.socialmediaapis.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.socialsapis.socialmediaapis.entity.Comment;
import com.socialsapis.socialmediaapis.entity.Post;
import com.socialsapis.socialmediaapis.entity.User;
import com.socialsapis.socialmediaapis.exceptions.PostNotFoundException;
import com.socialsapis.socialmediaapis.exceptions.UserNotFoundException;
import com.socialsapis.socialmediaapis.repository.CommentRepo;
import com.socialsapis.socialmediaapis.repository.PostRepo;
import com.socialsapis.socialmediaapis.repository.UserRepo;
import com.socialsapis.socialmediaapis.request.CommentRequest;

import java.math.BigInteger;
import java.time.LocalDate;
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

@ContextConfiguration(classes = {CommentServiceImpl.class})
@ExtendWith(SpringExtension.class)
class CommentServiceImplTest {
    @MockBean
    private CommentRepo commentRepo;

    @Autowired
    private CommentServiceImpl commentServiceImpl;

    @MockBean
    private PostRepo postRepo;

    @MockBean
    private UserRepo userRepo;


    @Test
    void testCommentOnPost() {
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
        post.setContent("the content");
        post.setCreatedDate(LocalDate.of(1970, 1, 1).atStartOfDay());
        post.setId(1L);
        post.setLikesCount(BigInteger.valueOf(1L));
        Optional<Post> ofResult2 = Optional.of(post);

        Post post2 = new Post();
        post2.setAuthorId(1L);
        post2.setComments(new ArrayList<>());
        post2.setContent("the content");
        post2.setCreatedDate(LocalDate.of(1970, 1, 1).atStartOfDay());
        post2.setId(1L);
        post2.setLikesCount(BigInteger.valueOf(1L));
        when(postRepo.save(Mockito.<Post>any())).thenReturn(post2);
        when(postRepo.findById(Mockito.<Long>any())).thenReturn(ofResult2);

        Post post3 = new Post();
        post3.setAuthorId(1L);
        post3.setComments(new ArrayList<>());
        post3.setContent("the content");
        post3.setCreatedDate(LocalDate.of(1970, 1, 1).atStartOfDay());
        post3.setId(1L);
        post3.setLikesCount(BigInteger.valueOf(1L));

        Comment comment = new Comment();
        comment.setComment("Comment");
        comment.setCommentatorId(1L);
        comment.setId(1L);
        comment.setPost(post3);
        when(commentRepo.save(Mockito.<Comment>any())).thenReturn(comment);

        CommentRequest commentRequest = new CommentRequest();
        commentRequest.setComment("Comment");
        commentRequest.setCommentatorId(1L);
        commentRequest.setPostId(1L);
        ResponseEntity<?> actualCommentOnPostResult = commentServiceImpl.commentOnPost(commentRequest);
        assertEquals("Comment added.", actualCommentOnPostResult.getBody());
        assertEquals(201, actualCommentOnPostResult.getStatusCodeValue());
        assertTrue(actualCommentOnPostResult.getHeaders().isEmpty());
        verify(userRepo).findById(Mockito.<Long>any());
        verify(postRepo).save(Mockito.<Post>any());
        verify(postRepo).findById(Mockito.<Long>any());
        verify(commentRepo).save(Mockito.<Comment>any());
    }

}

