package io.huyhoang.instagramclone.dto;


import java.io.Serializable;
import java.time.Instant;
import java.util.UUID;

public class UserResponse implements Serializable {

    private final UUID userId;

    private final String username;

    private final Instant createdAt;

    private final Instant updatedAt;

    private final ProfileResponse profile;

    public UserResponse(UUID userId,
                        String username,
                        Instant createdAt,
                        Instant updatedAt,
                        ProfileResponse profile) {
        this.userId = userId;
        this.username = username;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.profile = profile;
    }

    public UUID getUserId() {
        return userId;
    }

    public String getUsername() {
        return username;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public ProfileResponse getProfile() {
        return profile;
    }
}
