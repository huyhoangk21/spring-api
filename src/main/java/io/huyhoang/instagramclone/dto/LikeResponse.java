package io.huyhoang.instagramclone.dto;

import java.io.Serializable;
import java.util.UUID;

public class LikeResponse implements Serializable {

    private final UUID userId;

    public LikeResponse(UUID userId) {
        this.userId = userId;
    }

    public UUID getUserId() {
        return userId;
    }
}
