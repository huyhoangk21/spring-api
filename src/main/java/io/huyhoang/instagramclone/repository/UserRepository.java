package io.huyhoang.instagramclone.repository;

import io.huyhoang.instagramclone.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.awt.print.Pageable;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {

    Optional<User> findByEmail(String email);

    List<User> findByUsernameContainingIgnoreCase(String username);

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);
}
