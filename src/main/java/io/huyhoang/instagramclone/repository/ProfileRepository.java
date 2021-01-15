package io.huyhoang.instagramclone.repository;

import io.huyhoang.instagramclone.entity.Profile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ProfileRepository extends JpaRepository<Profile, UUID> {

}
