package com.edu.ulab.app.exception;

public class UserNotFoundException extends NotFoundException {
    public UserNotFoundException(String message) {
        super("Bad user: " + message);
    }
}
