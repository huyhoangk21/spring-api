package io.huyhoang.instagramclone.entity;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "profiles")
public class Profile extends Auditable{

    @Id
    @Column(name = "user_id")
    private UUID userId;

    @Column(name = "bio", columnDefinition = "TEXT")
    private String bio;

    @Column(name = "image_url")
    private String imageUrl;

    @Column(name = "website_url")
    private String websiteUrl;

    @OneToOne
    @MapsId
    @JoinColumn(name = "user_id")
    private User user;

    public Profile() {
    }

    public Profile(String bio, String imageUrl, String websiteUrl) {
        this.bio = bio;
        this.imageUrl = imageUrl;
        this.websiteUrl = websiteUrl;
    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "Profile{" +
                "userId=" + userId +
                ", bio='" + bio + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", websiteUrl='" + websiteUrl + '\'' +
                '}';
    }
}
