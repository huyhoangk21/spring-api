package io.huyhoang.instagramclone.dto;

import java.io.Serializable;
import java.time.Instant;
import java.util.UUID;

public class ProfileResponse implements Serializable {

    private final UUID profileId;

    private final String bio;

    private final String imageUrl;

    private final String websiteUrl;

    private final Instant createdAt;

    private final Instant updatedAt;

    public ProfileResponse(UUID profileId,
                           String bio,
                           String imageUrl,
                           String websiteUrl,
                           Instant createdAt,
                           Instant updatedAt) {
        this.profileId = profileId;
        this.bio = bio;
        this.imageUrl = imageUrl;
        this.websiteUrl = websiteUrl;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public UUID getProfileId() {
        return profileId;
    }

    public String getBio() {
        return bio;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getWebsiteUrl() {
        return websiteUrl;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }
}
