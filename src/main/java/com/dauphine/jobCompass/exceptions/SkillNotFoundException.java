package com.dauphine.jobCompass.exceptions;

public class SkillNotFoundException extends RuntimeException {
    public SkillNotFoundException() {
        super("Job introuvable.");
    }
}
