package com.mycompany.hotel.bookingsystem.exceptions;

public class InvalidBookingException extends Exception {
    public InvalidBookingException(String message) {
        super(message);
    }
}