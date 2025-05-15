package com.mycompany.hotel.bookingsystem.models.payment;

import com.mycompany.hotel.bookingsystem.exceptions.InvalidEmailException;


public final class PayPalPayment implements Payment {
    private final String email;


    public PayPalPayment(String email)  {
        this.email = email;
    }

  /*  private String validateEmail(String email) throws InvalidEmailException {

        String trimmedEmail = email.trim();

        if (trimmedEmail.isEmpty() || !email.contains("@")) {
            throw new InvalidEmailException();
        }
        return email;
    }*/

    @Override
    public boolean pay(double amount) throws IllegalArgumentException {
        validateAmount(amount);  // Reuses interface's default method
        return true;  // Mock success
    }

    public String getEmail() {
        return email;
    }
}