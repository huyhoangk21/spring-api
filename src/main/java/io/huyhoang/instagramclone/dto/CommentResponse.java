package io.huyhoang.instagramclone.dto;

import java.io.Serializable;
import java.time.Instant;
import java.util.UUID;

public class CommentResponse implements Serializable {

    private final UUID commentId;

    private final UUID postId;

    private final UUID userId;

    private final String content;

    private final Instant createdAt;

    private final Instant updatedAt;

    public CommentResponse(UUID commentId,
                           UUID postId,
                           UUID userId,
                           String content,
                           Instant createdAt,
                           Instant updatedAt) {
        this.commentId = commentId;
        this.postId = postId;
        this.userId = userId;
        this.content = content;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public UUID getCommentId() {
        return commentId;
    }

    public UUID getPostId() {
        return postId;
    }

    public UUID getUserId() {
        return userId;
    }

    public String getContent() {
        return content;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }
}
