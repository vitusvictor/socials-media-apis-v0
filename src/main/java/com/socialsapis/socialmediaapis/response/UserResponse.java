package com.socialsapis.socialmediaapis.response;

import lombok.Data;

import java.util.List;

@Data
public class UserResponse {
    private String username;
    private String email;
    private Long id;
    private List<UserResponse> followers;
    private List<UserResponse> following;
}
