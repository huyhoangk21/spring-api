package io.huyhoang.instagramclone.dto;

import javax.validation.constraints.NotBlank;

public class ProfileRequest {

    @NotBlank(message = "Bio cannot be empty")
    private String bio;

    private String imageUrl;

    private String websiteUrl;

    public ProfileRequest() {
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getWebsiteUrl() {
        return websiteUrl;
    }

    public void setWebsiteUrl(String websiteUrl) {
        this.websiteUrl = websiteUrl;
    }
}
