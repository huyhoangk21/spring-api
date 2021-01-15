package io.huyhoang.instagramclone.repository;

import io.huyhoang.instagramclone.entity.Post;
import io.huyhoang.instagramclone.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface PostRepository extends JpaRepository<Post, UUID> {

    List<Post> findAllByUserOrderByCreatedAtDesc(User user);
}
