package com.socialsapis.socialmediaapis.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class LikePostRequest {
    @NotNull(message = "Include post id.")
    private Long postId;

    @NotNull(message = "Include user id.")
    private Long userId;
}
