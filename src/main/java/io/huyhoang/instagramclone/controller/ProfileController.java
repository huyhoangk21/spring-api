package io.huyhoang.instagramclone.controller;

import io.huyhoang.instagramclone.dto.ProfileRequest;
import io.huyhoang.instagramclone.dto.ProfileResponse;
import io.huyhoang.instagramclone.service.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;

@RestController
@RequestMapping(value = "/api")
public class ProfileController {

    private final ProfileService profileService;

    @Autowired
    public ProfileController(ProfileService profileService) {
        this.profileService = profileService;
    }

    @GetMapping(value = "/users/{userId}/profiles")
    public ProfileResponse get(@PathVariable("userId") UUID userId) {
        return profileService.getProfile(userId);
    }

    @PutMapping(value = "/profiles")
    public ProfileResponse update(@Valid @RequestBody ProfileRequest profileRequest) {
        return profileService.updateProfile(profileRequest);
    }

}
