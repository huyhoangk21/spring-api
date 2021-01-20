package io.huyhoang.instagramclone.dto;

import java.io.Serializable;
import java.util.UUID;

public class UserSummaryResponse implements Serializable {

    private final UUID userId;

    private final String username;

    private final String imageUrl;

    public UserSummaryResponse(UUID userId,
                               String username,
                               String imageUrl) {
        this.userId = userId;
        this.username = username;
        this.imageUrl = imageUrl;
    }

    public UUID getUserId() {
        return userId;
    }

    public String getUsername() {
        return username;
    }

    public String getImageUrl() {
        return imageUrl;
    }
}
