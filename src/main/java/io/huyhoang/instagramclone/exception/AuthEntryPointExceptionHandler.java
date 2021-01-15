package io.huyhoang.instagramclone.exception;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.huyhoang.instagramclone.dto.UserError;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;

public class AuthEntryPointExceptionHandler implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException e) throws IOException, ServletException {

        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

        UserError userError = new UserError(Collections.singletonList("Unable to authenticate"));

        new ObjectMapper().writeValue(response.getOutputStream(), userError);
    }
}
