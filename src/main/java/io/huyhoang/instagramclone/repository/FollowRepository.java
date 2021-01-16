package io.huyhoang.instagramclone.repository;

import io.huyhoang.instagramclone.entity.Follow;
import io.huyhoang.instagramclone.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface FollowRepository extends JpaRepository<Follow, UUID> {

    Boolean existsByFollowedAndFollowing(User followed, User following);

    Optional<Follow> findByFollowedAndFollowing(User followed, User following);
}
