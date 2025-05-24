package com.dauphine.jobCompass.exceptions;

public class JobNotFoundException extends RuntimeException {
    public JobNotFoundException() {
        super("Job introuvable.");
    }
}
