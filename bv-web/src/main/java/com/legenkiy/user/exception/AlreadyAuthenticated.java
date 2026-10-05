package com.legenkiy.user.exception;

public class AlreadyAuthenticated extends RuntimeException {
    public AlreadyAuthenticated(String message) {
        super(message);
    }

    public AlreadyAuthenticated(String message, Throwable cause) {
        super(message, cause);
    }
}
