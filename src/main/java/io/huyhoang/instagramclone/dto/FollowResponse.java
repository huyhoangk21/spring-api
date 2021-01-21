package io.huyhoang.instagramclone.dto;

import java.io.Serializable;
import java.util.List;

public class FollowResponse implements Serializable {

    private final List<UserSummaryResponse> followers;

    private final List<UserSummaryResponse> following;

    public FollowResponse(List<UserSummaryResponse> followers, List<UserSummaryResponse> following) {
        this.followers = followers;
        this.following = following;
    }

    public List<UserSummaryResponse> getFollowers() {
        return followers;
    }

    public List<UserSummaryResponse> getFollowing() {
        return following;
    }
}
