package io.huyhoang.instagramclone.controller;

import io.huyhoang.instagramclone.service.LikeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping(value = "/api")
public class LikeController {

    private final LikeService likeService;

    @Autowired
    public LikeController(LikeService likeService) {
        this.likeService = likeService;
    }

    @PostMapping(value = "/posts/{postId}/like")
    public ResponseEntity<Void> likePost(@PathVariable("postId")UUID postId) {
        likeService.likePost(postId);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping(value = "/posts/{postId}/like")
    public ResponseEntity<Void> unlikePost(@PathVariable("postId")UUID postId) {
        likeService.unlikePost(postId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping(value = "/comments/{commentId}/like")
    public ResponseEntity<Void> likeComment(@PathVariable("commentId")UUID commentId) {
        likeService.likeComment(commentId);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping(value = "/comments/{commentId}/like")
    public ResponseEntity<Void> unlikeComment(@PathVariable("commentId")UUID commentId) {
        likeService.unlikeComment(commentId);
        return ResponseEntity.noContent().build();
    }
}
