package com.dauphine.jobCompass.exceptions;

public class UsernameNotFoundException  extends RuntimeException {
    public UsernameNotFoundException(String email) {
        super("user with email '" + email + "' not found");
    }
}
