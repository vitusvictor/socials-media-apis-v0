package com.socialsapis.socialmediaapis.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class FollowRequest {
    @NotNull(message = "Please provide user to follow id.")
    private Long followingId;
    @NotNull(message = "Please provide follower id.")
    private Long followerId;
}
