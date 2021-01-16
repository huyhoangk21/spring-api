package io.huyhoang.instagramclone.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "comment_likes")
public class CommentLike {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @Column(name = "like_id", unique = true, nullable = false, updatable = false)
    private UUID likeId;

    @ManyToOne
    @JoinColumn(name = "comment_id", nullable = false)
    private Comment comment;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public CommentLike() {
    }

    public CommentLike(Comment comment, User user) {
        this.comment = comment;
        this.user = user;
    }

    public UUID getLikeId() {
        return likeId;
    }

    public void setLikeId(UUID likeId) {
        this.likeId = likeId;
    }

    public Comment getComment() {
        return comment;
    }

    public void setComment(Comment comment) {
        this.comment = comment;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "CommentLike{" +
                "likeId=" + likeId +
                ", comment=" + comment +
                ", user=" + user +
                '}';
    }
}
