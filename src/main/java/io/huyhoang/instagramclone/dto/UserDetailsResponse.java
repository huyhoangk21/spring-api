package io.huyhoang.instagramclone.dto;

import java.io.Serializable;

public class UserDetailsResponse implements Serializable {

    private final String username;

    private final String bio;

    private final String websiteUrl;

    private final String imageUrl;

    private final int postsCount;

    private final int followersCount;

    private final int followingsCount;

    public UserDetailsResponse(String username,
                               String bio,
                               String websiteUrl,
                               String imageUrl,
                               int postsCount,
                               int followersCount,
                               int followingsCount) {
        this.username = username;
        this.bio = bio;
        this.websiteUrl = websiteUrl;
        this.imageUrl = imageUrl;
        this.postsCount = postsCount;
        this.followersCount = followersCount;
        this.followingsCount = followingsCount;
    }

    public String getUsername() {
        return username;
    }

    public String getBio() {
        return bio;
    }

    public String getWebsiteUrl() {
        return websiteUrl;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public int getPostsCount() {
        return postsCount;
    }

    public int getFollowersCount() {
        return followersCount;
    }

    public int getFollowingsCount() {
        return followingsCount;
    }
}
