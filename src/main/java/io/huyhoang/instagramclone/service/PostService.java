package io.huyhoang.instagramclone.service;

import io.huyhoang.instagramclone.dto.PostRequest;
import io.huyhoang.instagramclone.dto.PostResponse;
import io.huyhoang.instagramclone.entity.Post;
import io.huyhoang.instagramclone.entity.User;
import io.huyhoang.instagramclone.exception.ResourceNotFoundException;
import io.huyhoang.instagramclone.exception.UnauthorizedException;
import io.huyhoang.instagramclone.repository.PostRepository;
import io.huyhoang.instagramclone.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class PostService {

    private final UserRepository userRepository;
    private final PostRepository postRepository;

    @Autowired
    public PostService(UserRepository userRepository, PostRepository postRepository) {
        this.userRepository = userRepository;
        this.postRepository = postRepository;
    }

    @Transactional(readOnly = true)
    public List<PostResponse> getPostsByUser(UUID userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User does not exist"));
        return postRepository
                .findAllByUserOrderByCreatedAtDesc(user)
                .stream().map(this::convertDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public PostResponse addPost(PostRequest postRequest) {
        String userId = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findById(UUID.fromString(userId))
                .orElseThrow(() -> new ResourceNotFoundException("User does not exist"));
        Post post = new Post(postRequest.getImageUrl(), postRequest.getCaption(), user);
        postRepository.save(post);
        return convertDTO(post);
    }
    @Transactional
    public PostResponse editPost(PostRequest postRequest, UUID postId) {
        String userId = SecurityContextHolder.getContext().getAuthentication().getName();
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Post does not exist"));
        if (!post.getUser().getUserId().equals(UUID.fromString(userId))) {
            throw new UnauthorizedException("Unauthorized action");
        }
        post.setCaption(postRequest.getCaption());
        postRepository.save(post);
        return convertDTO(post);
    }

    @Transactional
    public void deletePost(UUID postId) {
        String userId = SecurityContextHolder.getContext().getAuthentication().getName();
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Post does not exist"));
        if (!post.getUser().getUserId().equals(UUID.fromString(userId))) {
            throw new UnauthorizedException("Unauthorized action");
        }
        postRepository.delete(post);
    }

    private PostResponse convertDTO(Post post) {
        return new PostResponse(
                post.getPostId(),
                post.getUser().getUserId(),
                post.getCaption(),
                post.getImageUrl(),
                post.getCreatedAt(),
                post.getUpdatedAt());
    }
}
