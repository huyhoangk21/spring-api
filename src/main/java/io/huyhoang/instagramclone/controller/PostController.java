package io.huyhoang.instagramclone.controller;

import io.huyhoang.instagramclone.dto.PostRequest;
import io.huyhoang.instagramclone.dto.PostResponse;
import io.huyhoang.instagramclone.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(value = "/api")
public class PostController {

    private final PostService postService;

    @Autowired
    public PostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping(value = "/users/{userId}/posts")
    public List<PostResponse> allByUser(@PathVariable("userId") UUID userId) {
        return postService.getPostsByUser(userId);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(value = "/posts")
    public PostResponse add(@Valid @RequestBody PostRequest postRequest) {
        return postService.addPost(postRequest);
    }

    @PutMapping(value = "/posts/{postId}")
    public PostResponse edit(@Valid @RequestBody PostRequest postRequest,
                             @PathVariable("postId") UUID postId) {
        return postService.editPost(postRequest, postId);
    }

    @DeleteMapping(value = "/posts/{postId}")
    public ResponseEntity<Void> delete(@PathVariable("postId") UUID postId) {
        postService.deletePost(postId);
        return ResponseEntity.noContent().build();
    }

}
