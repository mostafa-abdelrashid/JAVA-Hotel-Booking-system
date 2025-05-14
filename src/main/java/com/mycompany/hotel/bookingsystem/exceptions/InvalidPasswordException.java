package com.mycompany.hotel.bookingsystem.exceptions;

public class InvalidPasswordException extends Exception {
    private static final String DEFAULT_MESSAGE = "Invalid password provided";

    //creates an exception with default message

    public InvalidPasswordException() {
        super(DEFAULT_MESSAGE);
    }


    public InvalidPasswordException(String message) {
        super(message);
    }

}
