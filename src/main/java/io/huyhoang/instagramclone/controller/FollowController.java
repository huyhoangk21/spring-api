package io.huyhoang.instagramclone.controller;

import io.huyhoang.instagramclone.dto.FollowResponse;
import io.huyhoang.instagramclone.service.FollowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping(value = "/api/users/{userId}")
public class FollowController {

    private final FollowService followService;

    @Autowired
    public FollowController(FollowService followService) {
        this.followService = followService;
    }

    @PostMapping(value = "/follow")
    public ResponseEntity<Void> follow(@PathVariable("userId") UUID userId) {
        followService.follow(userId);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping(value = "/follow")
    public ResponseEntity<Void> unfollow(@PathVariable("userId") UUID userId) {
        followService.unfollow(userId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping(value = "/follow")
    public FollowResponse getFollows(@PathVariable("userId") UUID userId) {
        return followService.getFollows(userId);
    }
}
