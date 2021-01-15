package io.huyhoang.instagramclone.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class SignupRequest {

    @NotBlank(message = "Username must not be empty")
    @Size(min = 6, max = 15, message = "Username must be between 6-15 characters")
    private String username;

    @NotBlank(message = "Email must not be empty")
    @Email(message = "Email must be valid")
    private String email;

    @NotBlank(message = "Password must not be empty")
    @Size(min = 6, max = 15, message = "Password must be between 6-15 characters")
    private String password;

    public SignupRequest() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
