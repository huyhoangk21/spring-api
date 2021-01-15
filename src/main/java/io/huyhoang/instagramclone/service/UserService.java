package io.huyhoang.instagramclone.service;

import io.huyhoang.instagramclone.config.JwtConfig;
import io.huyhoang.instagramclone.dto.LoginRequest;
import io.huyhoang.instagramclone.dto.SignupRequest;
import io.huyhoang.instagramclone.dto.UserResponse;
import io.huyhoang.instagramclone.entity.Profile;
import io.huyhoang.instagramclone.entity.User;
import io.huyhoang.instagramclone.exception.ResourceAlreadyExistsException;
import io.huyhoang.instagramclone.exception.ResourceNotFoundException;
import io.huyhoang.instagramclone.repository.ProfileRepository;
import io.huyhoang.instagramclone.repository.UserRepository;
import io.jsonwebtoken.Jwt;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.parameters.P;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final ProfileRepository profileRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtConfig jwtConfig;

    @Autowired
    public UserService(UserRepository userRepository,
                       ProfileRepository profileRepository,
                       PasswordEncoder passwordEncoder,
                       AuthenticationManager authenticationManager,
                       JwtConfig jwtConfig) {
        this.userRepository = userRepository;
        this.profileRepository = profileRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtConfig = jwtConfig;
    }

    @Transactional
    public UserResponse signup(SignupRequest signupRequest) {

        if (userRepository.existsByUsername(signupRequest.getUsername())) {
            throw new ResourceAlreadyExistsException("Username already exists");
        }

        if (userRepository.existsByEmail(signupRequest.getEmail())) {
            throw new ResourceAlreadyExistsException("Email already exists");
        }

        User user = new User(signupRequest.getUsername(),
                signupRequest.getEmail(),
                passwordEncoder.encode(signupRequest.getPassword()));

        Profile profile = new Profile();
        user.setProfile(profile);
        userRepository.save(user);
        profileRepository.save(profile);

        return convertDTO(user);

    }

    @Transactional(readOnly = true)
    public ResponseEntity<UserResponse> login(LoginRequest loginRequest) {

        Authentication authentication = new UsernamePasswordAuthenticationToken(
                loginRequest.getEmail(),
                loginRequest.getPassword(),
                new HashSet<>());

        authenticationManager.authenticate(authentication);

        User user = userRepository.findByEmail(loginRequest.getEmail())
                .orElseThrow(() -> new ResourceNotFoundException("User does not exist"));

        String token = Jwts.builder()
                .setSubject(user.getUserId().toString())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + jwtConfig.getExpiry()))
                .signWith(SignatureAlgorithm.HS512, jwtConfig.getSecretKey())
                .compact();

        return ResponseEntity
                .ok()
                .header(jwtConfig.getAuthorizationHeader(), jwtConfig.getPrefix() + " " + token)
                .body(convertDTO(user));
    }

    @Transactional(readOnly = true)
    public List<UserResponse> allUsers() {
        return userRepository
                .findAll()
                .stream()
                .map(this::convertDTO)
                .collect(Collectors.toList());
    }

    private UserResponse convertDTO(User user) {
        return new UserResponse(
                user.getUserId(),
                user.getUsername(),
                user.getEmail(),
                user.getCreatedAt(),
                user.getUpdatedAt());
    }
}
