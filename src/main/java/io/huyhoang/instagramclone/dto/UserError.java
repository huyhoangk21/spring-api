package io.huyhoang.instagramclone.dto;

import java.io.Serializable;
import java.util.List;

public class UserError implements Serializable {

    private final List<String> errors;

    public UserError(List<String> errors) {
        this.errors = errors;
    }

    public List<String> getErrors() {
        return errors;
    }
}
