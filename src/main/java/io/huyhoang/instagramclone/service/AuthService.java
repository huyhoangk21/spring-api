package io.huyhoang.instagramclone.service;

import io.huyhoang.instagramclone.entity.Comment;
import io.huyhoang.instagramclone.entity.Post;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class AuthService {

    public UUID currentAuth() {
        return UUID.fromString(SecurityContextHolder.getContext().getAuthentication().getName());
    }

    public boolean ownPost(Post post) {
        return post.getUser().getUserId().equals(currentAuth());
    }

    public boolean ownComment(Comment comment) {
        return comment.getUser().getUserId().equals(currentAuth());
    }

}
