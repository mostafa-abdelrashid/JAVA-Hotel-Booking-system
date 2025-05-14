package com.mycompany.hotel.bookingsystem.exceptions;

public class InvalidCodeException extends RuntimeException {
    private static final String DEFAULT_MESSAGE = "Invalid Code provided";
    //creates an exception with default message

    public InvalidCodeException() {
        super(DEFAULT_MESSAGE);
    }

    public InvalidCodeException(String message) {

        super(message);
    }
}
