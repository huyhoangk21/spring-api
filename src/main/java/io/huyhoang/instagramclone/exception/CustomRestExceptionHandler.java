package io.huyhoang.instagramclone.exception;

import io.huyhoang.instagramclone.dto.ApiError;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

@ControllerAdvice
public class CustomRestExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers,
                                                                  HttpStatus status,
                                                                  WebRequest request) {
        List<String> errors = new LinkedList<>();
        ex.getBindingResult().getFieldErrors().forEach(error -> errors.add(error.getDefaultMessage()));
        ex.getBindingResult().getGlobalErrors().forEach(error -> errors.add(error.getDefaultMessage()));
        return handleExceptionInternal(ex, new ApiError(errors), headers, HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler(ResourceAlreadyExistsException.class)
    protected ResponseEntity<ApiError> handleResourceAlreadyExists(ResourceAlreadyExistsException ex) {
        return ResponseEntity.badRequest().body(new ApiError(Collections.singletonList(ex.getMessage())));
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    protected ResponseEntity<ApiError> handleResourceNotFound(ResourceNotFoundException ex) {
        return new ResponseEntity<>(new ApiError(Collections.singletonList(ex.getMessage())), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UnauthorizedException.class)
    protected ResponseEntity<ApiError> handleUnauthorized(UnauthorizedException ex) {
        return new ResponseEntity<>(new ApiError(Collections.singletonList(ex.getMessage())), HttpStatus.FORBIDDEN);
    }
}
