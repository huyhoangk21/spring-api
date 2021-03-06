package io.huyhoang.instagramclone.service;

import io.huyhoang.instagramclone.dto.PostRequest;
import io.huyhoang.instagramclone.dto.PostResponse;
import io.huyhoang.instagramclone.entity.Post;
import io.huyhoang.instagramclone.entity.User;
import io.huyhoang.instagramclone.exception.UnauthorizedException;
import io.huyhoang.instagramclone.repository.PostRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class PostService {

    private final PostRepository postRepository;
    private final UtilService utilService;
    Logger log = LoggerFactory.getLogger(PostService.class);

    @Autowired
    public PostService(PostRepository postRepository,
                       UtilService utilService) {
        this.postRepository = postRepository;
        this.utilService = utilService;
    }

    @Transactional(readOnly = true)
    public List<PostResponse> getPostsByUser(UUID userId) {
        User user = utilService.getUser(userId);
        return postRepository
                .findAllByUserOrderByCreatedAtDesc(user)
                .stream()
                .map(utilService::getPostResponse)
                .collect(Collectors.toList());
    }

    @Transactional
    public PostResponse addPost(PostRequest postRequest) {
        User user = utilService.getUser(utilService.currentAuth());

        Post post = new Post(postRequest.getImageUrl(), postRequest.getCaption(), user);

        postRepository.save(post);
        return utilService.getPostResponse(post);
    }
    @Transactional
    public PostResponse editPost(PostRequest postRequest, UUID postId) {
        Post post = utilService.getPost(postId);
        if (!utilService.ownPost(post)) {
            throw new UnauthorizedException();
        }
        post.setCaption(postRequest.getCaption());
        postRepository.save(post);
        return utilService.getPostResponse(post);
    }

    @Transactional
    public void deletePost(UUID postId) {
        Post post = utilService.getPost(postId);
        if (!utilService.ownPost(post)) {
            throw new UnauthorizedException();
        }
        postRepository.delete(post);
    }
}
