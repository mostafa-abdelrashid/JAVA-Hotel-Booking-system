package com.mycompany.hotel.bookingsystem.exceptions;

public class ExpiredCardException extends RuntimeException {
    public static final String DEFAULT_MESSAGE = "The card has expired";

    public ExpiredCardException() {
        super(DEFAULT_MESSAGE);
    }

    public ExpiredCardException(String message) {
        super(message);
    }
}
