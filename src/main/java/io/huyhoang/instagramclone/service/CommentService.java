package io.huyhoang.instagramclone.service;

import io.huyhoang.instagramclone.dto.CommentRequest;
import io.huyhoang.instagramclone.dto.CommentResponse;
import io.huyhoang.instagramclone.entity.Comment;
import io.huyhoang.instagramclone.entity.Post;
import io.huyhoang.instagramclone.entity.User;
import io.huyhoang.instagramclone.exception.ResourceNotFoundException;
import io.huyhoang.instagramclone.exception.UnauthorizedException;
import io.huyhoang.instagramclone.repository.CommentRepository;
import io.huyhoang.instagramclone.repository.PostRepository;
import io.huyhoang.instagramclone.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class CommentService {

    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;

    @Autowired
    public CommentService(UserRepository userRepository, PostRepository postRepository, CommentRepository commentRepository) {
        this.userRepository = userRepository;
        this.postRepository = postRepository;
        this.commentRepository = commentRepository;
    }

    @Transactional
    public CommentResponse addComment(CommentRequest commentRequest, UUID postId) {
        String userId = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.getOne(UUID.fromString(userId));
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Post does not exist"));

        Comment comment = new Comment(commentRequest.getContent(), user, post);

        commentRepository.save(comment);

        return convertDTO(comment);
    }

    @Transactional
    public CommentResponse editComment(CommentRequest commentRequest, UUID commentId) {
        String userId = SecurityContextHolder.getContext().getAuthentication().getName();
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new ResourceNotFoundException("Comment does not exist"));

        if (!comment.getUser().getUserId().equals(UUID.fromString(userId))) {
            throw new UnauthorizedException();
        }

        comment.setContent(commentRequest.getContent());
        commentRepository.save(comment);
        return convertDTO(comment);
    }

    @Transactional
    public void deleteComment(UUID commentId) {
        String userId = SecurityContextHolder.getContext().getAuthentication().getName();
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new ResourceNotFoundException("Comment does not exist"));

        if (!comment.getUser().getUserId().equals(UUID.fromString(userId))) {
            throw new UnauthorizedException();
        }

        commentRepository.delete(comment);
    }

    private CommentResponse convertDTO(Comment comment) {
        return new CommentResponse(
                comment.getCommentId(),
                comment.getPost().getPostId(),
                comment.getUser().getUserId(),
                comment.getContent(),
                comment.getCreatedAt(),
                comment.getUpdatedAt());
    }
}
