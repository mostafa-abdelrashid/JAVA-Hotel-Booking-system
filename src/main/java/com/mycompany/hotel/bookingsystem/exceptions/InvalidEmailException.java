package com.mycompany.hotel.bookingsystem.exceptions;

public class InvalidEmailException extends Exception {
    private static final String DEFAULT_MESSAGE = "Invalid Email provided";
    //creates an exception with default message


    public InvalidEmailException() {
        super(DEFAULT_MESSAGE);
    }

    public InvalidEmailException(String message) {

        super(message);
    }

}
