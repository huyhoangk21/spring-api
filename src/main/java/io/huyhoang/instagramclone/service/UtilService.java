package io.huyhoang.instagramclone.service;

import io.huyhoang.instagramclone.dto.CommentResponse;
import io.huyhoang.instagramclone.dto.PostResponse;
import io.huyhoang.instagramclone.dto.ProfileResponse;
import io.huyhoang.instagramclone.dto.UserResponse;
import io.huyhoang.instagramclone.entity.Comment;
import io.huyhoang.instagramclone.entity.Post;
import io.huyhoang.instagramclone.entity.Profile;
import io.huyhoang.instagramclone.entity.User;
import io.huyhoang.instagramclone.exception.ResourceAlreadyExistsException;
import io.huyhoang.instagramclone.exception.ResourceNotFoundException;
import io.huyhoang.instagramclone.exception.UnauthorizedException;
import io.huyhoang.instagramclone.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UtilService {

    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;
    private final FollowRepository followRepository;
    private final PostLikeRepository postLikeRepository;
    private final CommentLikeRepository commentLikeRepository;

    @Autowired
    public UtilService(UserRepository userRepository,
                       PostRepository postRepository,
                       CommentRepository commentRepository,
                       FollowRepository followRepository,
                       PostLikeRepository postLikeRepository,
                       CommentLikeRepository commentLikeRepository) {
        this.userRepository = userRepository;
        this.postRepository = postRepository;
        this.commentRepository = commentRepository;
        this.followRepository = followRepository;
        this.postLikeRepository = postLikeRepository;
        this.commentLikeRepository = commentLikeRepository;
    }

    public UUID currentAuth() {
        return UUID.fromString(SecurityContextHolder.getContext().getAuthentication().getName());
    }

    public boolean ownPost(Post post) {
        return post.getUser().getUserId().equals(currentAuth());
    }

    public boolean ownComment(Comment comment) {
        return comment.getUser().getUserId().equals(currentAuth());
    }

    public boolean canFollow(User followed, User following) {
        if (followed.getUserId().equals(following.getUserId())) {
            throw new UnauthorizedException();
        }
        if (followRepository.existsByFollowedAndFollowing(followed, following)) {
            throw new ResourceAlreadyExistsException("Already followed");
        }
        return true;
    }

    public boolean canUnfollow(User followed, User following) {
        if (followed.getUserId().equals(following.getUserId())) {
            throw new UnauthorizedException();
        }
        if (!followRepository.existsByFollowedAndFollowing(followed, following)) {
            throw new ResourceNotFoundException("Not yet followed");
        }
        return true;
    }

    public boolean canLikePost(Post post, User user) {
        if (postLikeRepository.existsByPostAndUser(post, user)) {
            throw new ResourceAlreadyExistsException("Post already liked");
        }
        return true;
    }

    public boolean canUnlikePost(Post post, User user) {
        if (!post.getUser().getUserId().equals(user.getUserId())) {
            throw new UnauthorizedException();
        }
        if (!postLikeRepository.existsByPostAndUser(post, user)) {
            throw new ResourceNotFoundException("Post not yet liked");
        }
        return true;
    }

    public boolean canLikeComment(Comment comment, User user) {
        if (commentLikeRepository.existsByCommentAndUser(comment, user)) {
            throw new ResourceAlreadyExistsException("Comment already liked");
        }
        return true;
    }

    public boolean canUnlikeComment(Comment comment, User user) {
        if (!comment.getUser().getUserId().equals(user.getUserId())) {
            throw new UnauthorizedException();
        }
        if (!commentLikeRepository.existsByCommentAndUser(comment, user)) {
            throw new ResourceNotFoundException("Comment not yet liked");
        }
        return true;
    }

    public User getUser(UUID userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User does not exist"));
    }

    public Post getPost(UUID postId) {
        return postRepository.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Post does not exist"));
    }

    public Comment getComment(UUID commentId) {
        return commentRepository.findById(commentId)
                .orElseThrow(() -> new ResourceNotFoundException("Comment does not exist"));
    }

    public UserResponse getUserResponse(User user) {
        return new UserResponse(
                user.getUserId(),
                user.getUsername(),
                user.getCreatedAt(),
                user.getUpdatedAt(),
                getProfileResponse(user.getProfile()));
    }

    public PostResponse getPostResponse(Post post) {
        return new PostResponse(
                post.getPostId(),
                post.getUser().getUserId(),
                post.getCaption(),
                post.getImageUrl(),
                post.getCreatedAt(),
                post.getUpdatedAt(),
                post.getComments().stream().map(this::getCommentResponse).collect(Collectors.toSet()));
    }

    public CommentResponse getCommentResponse(Comment comment) {
        return new CommentResponse(
                comment.getCommentId(),
                comment.getPost().getPostId(),
                comment.getUser().getUserId(),
                comment.getContent(),
                comment.getCreatedAt(),
                comment.getUpdatedAt());
    }

    public ProfileResponse getProfileResponse(Profile profile) {
        return new ProfileResponse(
                profile.getProfileId(),
                profile.getBio(),
                profile.getImageUrl(),
                profile.getWebsiteUrl(),
                profile.getCreatedAt(),
                profile.getUpdatedAt());
    }

}
