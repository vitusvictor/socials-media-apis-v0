package com.socialsapis.socialmediaapis.controller;

import com.socialsapis.socialmediaapis.request.*;
import com.socialsapis.socialmediaapis.response.SignUpResponse;
import com.socialsapis.socialmediaapis.service.*;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class APIs {

    private final Signin signin;
    private final SignUp signUp;
    private final PostService postService;
    private final CommentService commentService;
    private final FollowService followService;
    private final UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody SignUpRequest signUpRequest) {
        return ResponseEntity.ok(new SignUpResponse(signUp.signUp(signUpRequest)));
    }

    @PostMapping("/signin")
    public ResponseEntity<?> signin(@RequestBody SigninRequest signinRequest) {
        return ResponseEntity.ok(signin.signin(signinRequest));
    }

    @GetMapping("/get-all-users")
    public ResponseEntity<?> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/get-user/{user-id}")
    public ResponseEntity<?> getUser(@PathVariable("user-id") Long id) {
        return userService.getUser(id);
    }

    @PostMapping("/create-post")
    public ResponseEntity<?> createPost(@Valid @RequestBody CreatePostRequest createPostRequest) {
        return postService.createPost(createPostRequest);
    }

    @PatchMapping("/like-post")
    public ResponseEntity<?> likePost(@Valid @RequestBody LikePostRequest likePostRequest) {
        return postService.likePost(likePostRequest);
    }

    @GetMapping("/get-post/{post-id}")
    public ResponseEntity<?>  getPost(@PathVariable("post-id") Long id) {
        return postService.getPost(id);
    }

    @PostMapping("/make-comment")
    public ResponseEntity<?> comment(@Valid @RequestBody CommentRequest commentRequest) {
        return commentService.commentOnPost(commentRequest);
    }

    @PatchMapping("/follow")
    public ResponseEntity<?> follow(@Valid @RequestBody FollowRequest followRequest) {
        return followService.follow(followRequest);
    }

    @PatchMapping("/unfollow")
    public ResponseEntity<?> unfollow(@Valid @RequestBody FollowRequest followRequest) {
        return followService.unfollow(followRequest);
    }

}
