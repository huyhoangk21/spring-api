package io.huyhoang.instagramclone.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "post_likes")
public class PostLike {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @Column(name = "like_id", unique = true, nullable = false, updatable = false)
    private UUID likeId;

    @ManyToOne
    @JoinColumn(name = "post_id", nullable = false)
    private Post post;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public PostLike() {
    }

    public PostLike(Post post, User user) {
        this.post = post;
        this.user = user;
    }

    public UUID getLikeId() {
        return likeId;
    }

    public void setLikeId(UUID likeId) {
        this.likeId = likeId;
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "LikePost{" +
                "likeId=" + likeId +
                ", post=" + post +
                ", user=" + user +
                '}';
    }
}
