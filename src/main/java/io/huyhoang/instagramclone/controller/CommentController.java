package io.huyhoang.instagramclone.controller;

import io.huyhoang.instagramclone.dto.CommentRequest;
import io.huyhoang.instagramclone.dto.CommentResponse;
import io.huyhoang.instagramclone.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;

@RestController
@RequestMapping(value = "/api")
public class CommentController {

    private final CommentService commentService;

    @Autowired
    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(value = "/posts/{postId}/comments")
    public CommentResponse add(@Valid @RequestBody CommentRequest commentRequest,
                               @PathVariable("postId") UUID postId) {
        return commentService.addComment(commentRequest, postId);
    }

    @PutMapping(value = "/comments/{commentId}")
    public CommentResponse edit(@Valid @RequestBody CommentRequest commentRequest,
                                @PathVariable("commentId") UUID commentId) {
        return commentService.editComment(commentRequest, commentId);
    }

    @DeleteMapping(value = "/comments/{commentId}")
    public ResponseEntity<Void> delete(@PathVariable("commentId") UUID commentId) {
        commentService.deleteComment(commentId);
        return ResponseEntity.noContent().build();
    }
}
