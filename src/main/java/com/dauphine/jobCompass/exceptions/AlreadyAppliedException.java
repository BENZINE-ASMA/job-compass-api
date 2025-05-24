package com.dauphine.jobCompass.exceptions;

public class AlreadyAppliedException extends RuntimeException {
    public AlreadyAppliedException() {
        super("Vous avez déjà postulé à ce job.");
    }
}
