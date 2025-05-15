package com.mycompany.hotel.bookingsystem.models.payment;

import com.mycompany.hotel.bookingsystem.exceptions.ExpiredCardException;
import java.time.YearMonth;
import java.util.Objects;

public final class CreditCardPayment implements Payment {
    private final String cardNumber;
    private final YearMonth expiryDate;
    private final String cardHolderName;
    private final String cvv;

    public CreditCardPayment(String cardNumber, YearMonth expiryDate,
                             String cardHolderName, String cvv)
            throws IllegalArgumentException, ExpiredCardException {

        validateCardNumber(cardNumber);
        validateCVV(cvv);
        Objects.requireNonNull(cardHolderName, "Cardholder name cannot be null");
        Objects.requireNonNull(expiryDate, "Expiry date cannot be null");

        if (expiryDate.isBefore(YearMonth.now())) {
            throw new ExpiredCardException();
        }

        this.cardNumber = cardNumber;
        this.expiryDate = expiryDate;
        this.cardHolderName = cardHolderName.trim();
        this.cvv = cvv;
    }

    // Validation helpers -  Simplified
    private void validateCardNumber(String cardNumber) throws IllegalArgumentException {
        if (cardNumber == null || cardNumber.isEmpty()) {
            throw new IllegalArgumentException("Card number cannot be empty");
        }
    }

    private void validateCVV(String cvv) throws IllegalArgumentException {
        if (cvv == null || cvv.isEmpty()) {
            throw new IllegalArgumentException("CVV cannot be empty");
        }
    }

    @Override
    public boolean pay(double amount) throws IllegalArgumentException {
        validateAmount(amount);  // Reuses interface's default method
        // Actual payment gateway integration would go here
        return true;  // Mock success
    }

    public YearMonth getExpiryDate() {
        return expiryDate;
    }
}
