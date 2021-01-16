package io.huyhoang.instagramclone.dto;

import java.io.Serializable;
import java.time.Instant;
import java.util.Set;
import java.util.UUID;

public class PostResponse implements Serializable {

    private final UUID postId;

    private final UUID userId;

    private final String caption;

    private final String imageUrl;

    private final Instant createdAt;

    private final Instant updatedAt;

    private final Set<LikeResponse> likes;

    private final Set<CommentResponse> comments;

    public PostResponse(UUID postId,
                        UUID userId,
                        String caption,
                        String imageUrl,
                        Instant createdAt,
                        Instant updatedAt,
                        Set<LikeResponse> likes,
                        Set<CommentResponse> comments) {
        this.postId = postId;
        this.userId = userId;
        this.caption = caption;
        this.imageUrl = imageUrl;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.likes = likes;
        this.comments = comments;
    }

    public UUID getPostId() {
        return postId;
    }

    public UUID getUserId() {
        return userId;
    }

    public String getCaption() {
        return caption;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public Set<CommentResponse> getComments() {
        return comments;
    }

    public Set<LikeResponse> getLikes() {
        return likes;
    }
}
