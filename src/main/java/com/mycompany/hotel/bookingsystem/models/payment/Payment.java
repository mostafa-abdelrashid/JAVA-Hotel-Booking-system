package com.mycompany.hotel.bookingsystem.models.payment;

public interface Payment {
    boolean pay(double amount) throws IllegalArgumentException;

    default void validateAmount(double amount) throws IllegalArgumentException {
        if (amount < 0) {
            throw new IllegalArgumentException("Amount must be positive");
        }
    }
}