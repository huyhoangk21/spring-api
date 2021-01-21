package io.huyhoang.instagramclone.service;

import io.huyhoang.instagramclone.dto.*;
import io.huyhoang.instagramclone.entity.*;
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

    public UserSummaryResponse getUserSummaryResponse(User user) {
        return new UserSummaryResponse(
                user.getUserId(),
                user.getUsername(),
                user.getProfile().getImageUrl());
    }

    public UserDetailsResponse getUserDetailsResponse(User user) {
        return new UserDetailsResponse(
                user.getUsername(),
                user.getProfile().getBio(),
                user.getProfile().getWebsiteUrl(),
                user.getProfile().getImageUrl(),
                user.getPosts().size(),
                user.getFollowed().size(),
                user.getFollowing().size());
    }

    public PostResponse getPostResponse(Post post) {
        return new PostResponse(
                post.getPostId(),
                post.getUser().getUserId(),
                post.getCaption(),
                post.getImageUrl(),
                post.getCreatedAt(),
                post.getUpdatedAt(),
                post.getLikes().stream().map(this::getPostLikeResponse).collect(Collectors.toSet()),
                post.getComments().stream().map(this::getCommentResponse).collect(Collectors.toSet()));
    }

    public CommentResponse getCommentResponse(Comment comment) {
        return new CommentResponse(
                comment.getCommentId(),
                comment.getPost().getPostId(),
                comment.getUser().getUserId(),
                comment.getContent(),
                comment.getCreatedAt(),
                comment.getUpdatedAt(),
                comment.getLikes().stream().map(this::getCommentLikeResponse).collect(Collectors.toSet()));
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

    public LikeResponse getPostLikeResponse(PostLike postLike) {
        return new LikeResponse(postLike.getUser().getUserId());
    }

    public LikeResponse getCommentLikeResponse(CommentLike commentLike) {
        return new LikeResponse(commentLike.getUser().getUserId());
    }

}
