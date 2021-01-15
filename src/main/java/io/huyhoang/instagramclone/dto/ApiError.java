package io.huyhoang.instagramclone.dto;

import java.io.Serializable;
import java.util.List;

public class ApiError implements Serializable {

    private final List<String> errors;

    public ApiError(List<String> errors) {
        this.errors = errors;
    }

    public List<String> getErrors() {
        return errors;
    }
}
