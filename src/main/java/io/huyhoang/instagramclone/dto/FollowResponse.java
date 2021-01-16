package io.huyhoang.instagramclone.dto;

import java.io.Serializable;
import java.util.List;

public class FollowResponse implements Serializable {

    private final List<UserResponse> followers;

    private final List<UserResponse> following;

    public FollowResponse(List<UserResponse> followers, List<UserResponse> following) {
        this.followers = followers;
        this.following = following;
    }

    public List<UserResponse> getFollowers() {
        return followers;
    }

    public List<UserResponse> getFollowing() {
        return following;
    }
}
