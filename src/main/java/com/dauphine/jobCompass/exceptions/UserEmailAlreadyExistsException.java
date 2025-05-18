package com.dauphine.jobCompass.exceptions;

public class UserEmailAlreadyExistsException extends RuntimeException {
    public UserEmailAlreadyExistsException(String email) {
        super("user email already exists: " + email);
    }
}
