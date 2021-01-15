package io.huyhoang.instagramclone.service;

import io.huyhoang.instagramclone.entity.Comment;
import io.huyhoang.instagramclone.entity.Post;
import io.huyhoang.instagramclone.entity.User;
import io.huyhoang.instagramclone.exception.ResourceNotFoundException;
import io.huyhoang.instagramclone.repository.CommentRepository;
import io.huyhoang.instagramclone.repository.PostRepository;
import io.huyhoang.instagramclone.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UtilService {

    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;

    @Autowired
    public UtilService(UserRepository userRepository,
                       PostRepository postRepository,
                       CommentRepository commentRepository) {
        this.userRepository = userRepository;
        this.postRepository = postRepository;
        this.commentRepository = commentRepository;
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

}
