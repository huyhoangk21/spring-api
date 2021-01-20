package io.huyhoang.instagramclone.service;

import io.huyhoang.instagramclone.config.JwtConfig;
import io.huyhoang.instagramclone.dto.LoginRequest;
import io.huyhoang.instagramclone.dto.SignupRequest;
import io.huyhoang.instagramclone.dto.UserResponse;
import io.huyhoang.instagramclone.dto.UserSummaryResponse;
import io.huyhoang.instagramclone.entity.Profile;
import io.huyhoang.instagramclone.entity.User;
import io.huyhoang.instagramclone.exception.ResourceAlreadyExistsException;
import io.huyhoang.instagramclone.exception.ResourceNotFoundException;
import io.huyhoang.instagramclone.repository.ProfileRepository;
import io.huyhoang.instagramclone.repository.UserRepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
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
    private final UtilService utilService;
    private final JwtConfig jwtConfig;

    @Autowired
    public UserService(UserRepository userRepository,
                       ProfileRepository profileRepository,
                       PasswordEncoder passwordEncoder,
                       AuthenticationManager authenticationManager,
                       UtilService utilService,
                       JwtConfig jwtConfig) {
        this.userRepository = userRepository;
        this.profileRepository = profileRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.utilService = utilService;
        this.jwtConfig = jwtConfig;
    }


    @Transactional
    public UserSummaryResponse signup(SignupRequest signupRequest) {

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

        return utilService.getUserSummaryResponse(user);

    }

    @Transactional(readOnly = true)
    public ResponseEntity<UserSummaryResponse> login(LoginRequest loginRequest) {

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
                .body(utilService.getUserSummaryResponse(user));
    }

    @Transactional(readOnly = true)
    public List<UserResponse> allUsers() {
        return userRepository
                .findAll()
                .stream()
                .map(utilService::getUserResponse)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public UserSummaryResponse me() {
        return utilService.getUserSummaryResponse(utilService.getUser(utilService.currentAuth()));
    }
}
