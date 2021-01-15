package io.huyhoang.instagramclone.dto;


import java.io.Serializable;
import java.time.Instant;
import java.util.UUID;

public class UserResponse implements Serializable {

    private final UUID userId;

    private final String username;

    private final String email;

    private final Instant createdAt;

    private final Instant updatedAt;

    private final ProfileResponse profile;

    public UserResponse(UUID userId,
                        String username,
                        String email,
                        Instant createdAt,
                        Instant updatedAt,
                        ProfileResponse profile) {
        this.userId = userId;
        this.username = username;
        this.email = email;
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

    public String getEmail() {
        return email;
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
