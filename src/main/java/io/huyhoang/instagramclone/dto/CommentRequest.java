package io.huyhoang.instagramclone.dto;

import javax.validation.constraints.NotBlank;

public class CommentRequest {

    @NotBlank(message = "Content must not be empty")
    private String content;

    public CommentRequest() {
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
