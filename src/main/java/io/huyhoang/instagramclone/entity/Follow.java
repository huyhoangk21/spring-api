package io.huyhoang.instagramclone.entity;


import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "follows")
public class Follow extends Auditable{

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @Column(name = "follow_id", unique = true, nullable = false, updatable = false)
    private UUID followId;

    @ManyToOne
    @JoinColumn(name = "followed_id", nullable = false)
    private User followed;

    @ManyToOne
    @JoinColumn(name = "following_id", nullable = false)
    private User following;

    public Follow() {
    }

    public Follow(User followed,
                  User following) {
        this.followed = followed;
        this.following = following;
    }

    public UUID getFollowId() {
        return followId;
    }

    public void setFollowId(UUID followId) {
        this.followId = followId;
    }

    public User getFollowed() {
        return followed;
    }

    public void setFollowed(User followed) {
        this.followed = followed;
    }

    public User getFollowing() {
        return following;
    }

    public void setFollowing(User following) {
        this.following = following;
    }

    @Override
    public String toString() {
        return "Follow{" +
                "followId=" + followId +
                ", followed=" + followed +
                ", following=" + following +
                '}';
    }
}
