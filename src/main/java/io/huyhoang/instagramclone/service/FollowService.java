package io.huyhoang.instagramclone.service;

import io.huyhoang.instagramclone.dto.FollowResponse;
import io.huyhoang.instagramclone.dto.UserSummaryResponse;
import io.huyhoang.instagramclone.entity.Follow;
import io.huyhoang.instagramclone.entity.User;
import io.huyhoang.instagramclone.exception.ResourceNotFoundException;
import io.huyhoang.instagramclone.repository.FollowRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class FollowService {


    private final FollowRepository followRepository;
    private final UtilService utilService;

    @Autowired
    public FollowService(FollowRepository followRepository, UtilService utilService) {
        this.followRepository = followRepository;
        this.utilService = utilService;
    }

    @Transactional
    public void follow(UUID followingId) {
        User following = utilService.getUser(utilService.currentAuth());
        User followed = utilService.getUser(followingId);
        if (utilService.canFollow(followed, following)) {
            Follow follow = new Follow(followed, following);
            followRepository.save(follow);
        }
    }

    @Transactional
    public void unfollow(UUID followingId) {
        User following = utilService.getUser(utilService.currentAuth());
        User followed = utilService.getUser(followingId);
        if (utilService.canUnfollow(followed, following)) {
            Follow follow = followRepository.findByFollowedAndFollowing(followed, following)
                    .orElseThrow(() -> new ResourceNotFoundException("Follow does not exists"));
            followRepository.delete(follow);
        }
    }

    @Transactional
    public FollowResponse getFollows(UUID userId) {
        User user = utilService.getUser(userId);

        List<UserSummaryResponse> followers = user.getFollowed()
                .stream()
                .map(follow -> utilService.getUserSummaryResponse(follow.getFollowing()))
                .collect(Collectors.toList());

        List<UserSummaryResponse> following = user.getFollowing()
                .stream()
                .map(follow -> utilService.getUserSummaryResponse(follow.getFollowed()))
                .collect(Collectors.toList());

        return new FollowResponse(followers, following);
    }
}
