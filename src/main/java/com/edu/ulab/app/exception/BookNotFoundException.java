package com.edu.ulab.app.exception;

public class BookNotFoundException extends NotFoundException {
    public BookNotFoundException(String message) {
        super("Bad book: " + message);
    }
}
