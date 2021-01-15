package io.huyhoang.instagramclone.service;

import io.huyhoang.instagramclone.dto.ProfileRequest;
import io.huyhoang.instagramclone.dto.ProfileResponse;
import io.huyhoang.instagramclone.entity.Profile;
import io.huyhoang.instagramclone.entity.User;
import io.huyhoang.instagramclone.repository.ProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class ProfileService {

    private final ProfileRepository profileRepository;
    private final UtilService utilService;

    @Autowired
    public ProfileService(ProfileRepository profileRepository,
                          UtilService utilService) {
        this.profileRepository = profileRepository;
        this.utilService = utilService;
    }

    @Transactional(readOnly = true)
    public ProfileResponse getProfile(UUID userId) {
        User user = utilService.getUser(userId);
        return convertDTO(user.getProfile());

    }

    @Transactional
    public ProfileResponse updateProfile(ProfileRequest profileRequest) {
        User user = utilService.getUser(utilService.currentAuth());
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
