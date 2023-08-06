package com.socialsapis.socialmediaapis.request;

import lombok.Data;

@Data
public class CommentRequest {

    private Long postId;

    private String comment;

    private Long commentatorId;
}
