package io.huyhoang.instagramclone.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "profiles")
public class Profile extends Auditable{

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @Column(name = "profile_id", nullable = false, unique = true)
    private UUID profileId;

    @Column(name = "bio", columnDefinition = "TEXT")
    private String bio;

    @Column(name = "image_url")
    private String imageUrl;

    @Column(name = "website_url")
    private String websiteUrl;

    @OneToOne(mappedBy = "profile")
    private User user;

    public Profile() {
        this.bio = "";
        this.imageUrl = "";
        this.websiteUrl = "";
    }

    public Profile(String bio, String imageUrl, String websiteUrl) {
        this.bio = bio;
        this.imageUrl = imageUrl;
        this.websiteUrl = websiteUrl;
    }

    public UUID getProfileId() {
        return profileId;
    }

    public void setProfileId(UUID profileId) {
        this.profileId = profileId;
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
                "profileId=" + profileId +
                ", bio='" + bio + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", websiteUrl='" + websiteUrl + '\'' +
                ", user=" + user +
                '}';
    }
}
