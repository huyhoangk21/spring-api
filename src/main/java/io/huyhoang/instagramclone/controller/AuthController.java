package io.huyhoang.instagramclone.controller;

import io.huyhoang.instagramclone.dto.LoginRequest;
import io.huyhoang.instagramclone.dto.SignupRequest;
import io.huyhoang.instagramclone.dto.UserResponse;
import io.huyhoang.instagramclone.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/api/auth")
public class AuthController {


    private final UserService userService;


    @Autowired
    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping(value = "/signup")
    @ResponseStatus(HttpStatus.CREATED)
    public UserResponse signup(@Valid @RequestBody SignupRequest signupRequest) {
        return userService.signup(signupRequest);
    }

    @PostMapping(value = "/login")
    public ResponseEntity<UserResponse> login(@Valid @RequestBody LoginRequest loginRequest) {
        return userService.login(loginRequest);
    }

    @GetMapping(value = "/me")
    public UserResponse me() {
        return userService.me();
    }
}
