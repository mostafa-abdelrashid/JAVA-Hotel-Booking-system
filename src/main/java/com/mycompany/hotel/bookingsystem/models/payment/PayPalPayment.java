package com.mycompany.hotel.bookingsystem.models.payment;

import com.mycompany.hotel.bookingsystem.exceptions.InvalidEmailException;

public class PayPalPayment implements Payment {
    private String paypalEmail;

    public PayPalPayment(String paypalEmail) throws InvalidEmailException {
        if (paypalEmail == null || paypalEmail.trim().isEmpty()) throw new InvalidEmailException("There is no Email");
        else if (paypalEmail.contains("@.") || paypalEmail.contains(" ") || !paypalEmail.contains(".") || !paypalEmail.contains("@")) {
            throw new InvalidEmailException();
        }
        this.paypalEmail = paypalEmail;
    }

    public boolean pay(double amount) throws IllegalArgumentException {
        if (amount < 0) throw new IllegalArgumentException("the amount must be a positive value");
        return true; // assume the user have enough balance in their bank account
    }
}
