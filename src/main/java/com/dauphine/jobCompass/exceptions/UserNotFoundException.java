package com.dauphine.jobCompass.exceptions;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException() {
        super("Utilisateur introuvable.");
    }
}
