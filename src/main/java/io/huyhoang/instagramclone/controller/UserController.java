package io.huyhoang.instagramclone.controller;

import io.huyhoang.instagramclone.dto.UserResponse;
import io.huyhoang.instagramclone.dto.UserSummaryResponse;
import io.huyhoang.instagramclone.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<UserResponse> all() {
        return userService.allUsers();
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<UserSummaryResponse> allByUsername(@RequestParam("username") String username) {
        return userService.allByUsername(username);
    }
}
