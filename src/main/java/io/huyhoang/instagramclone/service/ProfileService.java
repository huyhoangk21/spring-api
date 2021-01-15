package io.huyhoang.instagramclone.service;

import io.huyhoang.instagramclone.dto.ProfileRequest;
import io.huyhoang.instagramclone.dto.ProfileResponse;
import io.huyhoang.instagramclone.entity.Profile;
import io.huyhoang.instagramclone.entity.User;
import io.huyhoang.instagramclone.exception.ResourceNotFoundException;
import io.huyhoang.instagramclone.repository.ProfileRepository;
import io.huyhoang.instagramclone.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class ProfileService {

    private final UserRepository userRepository;
    private final ProfileRepository profileRepository;
    private final AuthService authService;

    @Autowired
    public ProfileService(UserRepository userRepository,
                          ProfileRepository profileRepository,
                          AuthService authService) {
        this.userRepository = userRepository;
        this.profileRepository = profileRepository;
        this.authService = authService;
    }

    @Transactional(readOnly = true)
    public ProfileResponse getProfile(UUID userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User does not exist"));
        return convertDTO(user.getProfile());

    }

    @Transactional
    public ProfileResponse updateProfile(ProfileRequest profileRequest) {
        User user = userRepository.getOne(authService.currentAuth());
        Profile profile = user.getProfile();
        profile.setBio(profileRequest.getBio());
        profile.setImageUrl(profileRequest.getImageUrl());
        profile.setWebsiteUrl(profileRequest.getWebsiteUrl());
        profileRepository.save(profile);
        return convertDTO(profile);
    }

    private ProfileResponse convertDTO(Profile profile) {
        return new ProfileResponse(
                profile.getProfileId(),
                profile.getBio(),
                profile.getImageUrl(),
                profile.getWebsiteUrl(),
                profile.getCreatedAt(),
                profile.getUpdatedAt());
    }

}
