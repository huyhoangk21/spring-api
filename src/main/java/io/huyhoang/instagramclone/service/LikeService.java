package io.huyhoang.instagramclone.service;

import io.huyhoang.instagramclone.entity.*;
import io.huyhoang.instagramclone.exception.ResourceNotFoundException;
import io.huyhoang.instagramclone.repository.CommentLikeRepository;
import io.huyhoang.instagramclone.repository.PostLikeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class LikeService {

    private final PostLikeRepository postLikeRepository;
    private final CommentLikeRepository commentLikeRepository;
    private final UtilService utilService;

    @Autowired
    public LikeService(PostLikeRepository postLikeRepository,
                       CommentLikeRepository commentLikeRepository,
                       UtilService utilService) {
        this.postLikeRepository = postLikeRepository;
        this.commentLikeRepository = commentLikeRepository;
        this.utilService = utilService;
    }

    @Transactional
    public void likePost(UUID postId) {
        Post post = utilService.getPost(postId);
        User user = utilService.getUser(utilService.currentAuth());
        if (utilService.canLikePost(post, user)) {
            PostLike postLike = new PostLike(post, user);
            postLikeRepository.save(postLike);
        }
    }


    @Transactional
    public void unlikePost(UUID postId) {
        Post post = utilService.getPost(postId);
        User user = utilService.getUser(utilService.currentAuth());
        if (utilService.canUnlikePost(post, user)) {
            PostLike postLike = postLikeRepository.findByPostAndUser(post, user)
                    .orElseThrow(() -> new ResourceNotFoundException("Post Like does not exist"));
        }
    }


    @Transactional
    public void likeComment(UUID commentId) {
        Comment comment = utilService.getComment(commentId);
        User user = utilService.getUser(utilService.currentAuth());
        if (utilService.canLikeComment(comment, user)) {
            CommentLike commentLike = new CommentLike(comment, user);
            commentLikeRepository.save(commentLike);
        }
    }

    @Transactional
    public void unlikeComment(UUID commentId) {
        Comment comment = utilService.getComment(commentId);
        User user = utilService.getUser(utilService.currentAuth());
        if (utilService.canUnlikeComment(comment, user)) {
            CommentLike commentLike = commentLikeRepository.findByCommentAndUser(comment, user)
                    .orElseThrow(() -> new ResourceNotFoundException("Comment Like does not exist"));
        }
    }
}
