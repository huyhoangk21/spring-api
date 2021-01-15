package io.huyhoang.instagramclone.dto;

import javax.validation.constraints.NotBlank;

public class PostRequest {

    @NotBlank(message = "Caption must not be empty")
    private String caption;

    @NotBlank(message = "Image url must not be empty")
    private String imageUrl;

    public PostRequest() {
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
