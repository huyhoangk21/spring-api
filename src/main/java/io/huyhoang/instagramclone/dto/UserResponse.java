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

    public UserResponse(UUID userId,
                        String username,
                        String email,
                        Instant createdAt,
                        Instant updatedAt) {
        this.userId = userId;
        this.username = username;
        this.email = email;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
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
}
