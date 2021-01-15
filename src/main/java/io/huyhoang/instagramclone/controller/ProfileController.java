package io.huyhoang.instagramclone.controller;

import io.huyhoang.instagramclone.dto.ProfileResponse;
import io.huyhoang.instagramclone.entity.Profile;
import io.huyhoang.instagramclone.service.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping(value = "/api/users/{userId}/profiles")
public class ProfileController {

    private final ProfileService profileService;

    @Autowired
    public ProfileController(ProfileService profileService) {
        this.profileService = profileService;
    }

    @GetMapping
    public ProfileResponse get(@PathVariable("userId") UUID userId) {
        return profileService.getProfile(userId);
    }

}
