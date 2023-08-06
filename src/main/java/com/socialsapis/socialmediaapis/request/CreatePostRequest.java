package com.socialsapis.socialmediaapis.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CreatePostRequest {
    private String content;
    @NotNull(message = "Please include author id.")
    private Long userId;
}
