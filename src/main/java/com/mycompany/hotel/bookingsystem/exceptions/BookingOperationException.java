package com.mycompany.hotel.bookingsystem.exceptions;

public class BookingOperationException extends RuntimeException {
    public BookingOperationException(String message) {
        super(message);
    }
    public BookingOperationException(String message, Throwable cause) {
        super(message, cause);
    }
}