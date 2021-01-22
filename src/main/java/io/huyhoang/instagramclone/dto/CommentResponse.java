package io.huyhoang.instagramclone.dto;

import java.io.Serializable;
import java.time.Instant;
import java.util.Set;
import java.util.UUID;

public class CommentResponse implements Serializable {

    private final UUID commentId;

    private final UUID postId;

    private final UUID userId;

    private final String content;

    private final Instant createdAt;

    private final Instant updatedAt;

    private final Set<LikeResponse> likes;

    public CommentResponse(UUID commentId,
                           UUID postId,
                           UUID userId,
                           String content,
                           Instant createdAt,
                           Instant updatedAt,
                           Set<LikeResponse> likes) {
        this.commentId = commentId;
        this.postId = postId;
        this.userId = userId;
        this.content = content;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.likes = likes;
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

    public Set<LikeResponse> getLikes() {
        return likes;
    }
}
