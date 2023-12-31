package com.socialsapis.socialmediaapis.response;

import lombok.Data;

import java.util.List;

@Data
public class UserResponse {
    private String username;
    private String email;
    private Long id;
    private List<FollowerResponse> followers;
    private List<FollowerResponse> following;
}
