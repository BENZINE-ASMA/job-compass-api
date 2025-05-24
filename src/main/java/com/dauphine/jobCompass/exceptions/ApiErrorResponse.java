package com.dauphine.jobCompass.exceptions;

public class ApiErrorResponse extends RuntimeException{
    private String message;

    public ApiErrorResponse(String message) {
        this.message = message;
    }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
}
