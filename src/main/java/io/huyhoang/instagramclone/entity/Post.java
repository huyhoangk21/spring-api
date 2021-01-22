package io.huyhoang.instagramclone.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "posts")
public class Post extends Auditable{

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @Column(name = "post_id", unique = true, nullable = false, updatable = false)
    private UUID postId;

    @Column(name = "image_url", nullable = false)
    private String imageUrl;

    @Column(name = "caption", nullable = false)
    private String caption;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
    private Set<Comment> comments;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
    private Set<PostLike> likes;

    public Post() {
    }

    public Post(String imageUrl, String caption, User user) {
        this.imageUrl = imageUrl;
        this.caption = caption;
        this.user = user;
        this.likes = new HashSet<>();
        this.comments = new HashSet<>();
    }

    public UUID getPostId() {
        return postId;
    }

    public void setPostId(UUID postId) {
        this.postId = postId;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Set<Comment> getComments() {
        return comments;
    }

    public void setComments(Set<Comment> comments) {
        this.comments = comments;
    }

    public Set<PostLike> getLikes() {
        return likes;
    }

    public void setLikes(Set<PostLike> likes) {
        this.likes = likes;
    }

    @Override
    public String toString() {
        return "Post{" +
                "postId=" + postId +
                ", imageUrl='" + imageUrl + '\'' +
                ", caption='" + caption + '\'' +
                ", user=" + user +
                ", comments=" + comments +
                ", likes=" + likes +
                '}';
    }
}
