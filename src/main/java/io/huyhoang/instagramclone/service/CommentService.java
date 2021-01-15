package io.huyhoang.instagramclone.service;

import io.huyhoang.instagramclone.dto.CommentRequest;
import io.huyhoang.instagramclone.dto.CommentResponse;
import io.huyhoang.instagramclone.entity.Comment;
import io.huyhoang.instagramclone.entity.Post;
import io.huyhoang.instagramclone.entity.User;
import io.huyhoang.instagramclone.exception.UnauthorizedException;
import io.huyhoang.instagramclone.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class CommentService {

    private final CommentRepository commentRepository;
    private final UtilService utilService;

    @Autowired
    public CommentService(CommentRepository commentRepository,
                          UtilService utilService) {
        this.commentRepository = commentRepository;
        this.utilService = utilService;
    }

    @Transactional
    public CommentResponse addComment(CommentRequest commentRequest, UUID postId) {
        User user = utilService.getUser(utilService.currentAuth());
        Post post = utilService.getPost(postId);
        Comment comment = new Comment(commentRequest.getContent(), user, post);
        commentRepository.save(comment);
        return convertDTO(comment);
    }

    @Transactional
    public CommentResponse editComment(CommentRequest commentRequest, UUID commentId) {
        Comment comment = utilService.getComment(commentId);
        if (!utilService.ownComment(comment)) {
            throw new UnauthorizedException();
        }
        comment.setContent(commentRequest.getContent());
        commentRepository.save(comment);
        return convertDTO(comment);
    }

    @Transactional
    public void deleteComment(UUID commentId) {
        Comment comment = utilService.getComment(commentId);
        if (!utilService.ownComment(comment)) {
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
