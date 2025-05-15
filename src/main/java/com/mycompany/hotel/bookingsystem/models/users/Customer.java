package com.mycompany.hotel.bookingsystem.models.users;

import com.mycompany.hotel.bookingsystem.exceptions.InvalidEmailException;
import com.mycompany.hotel.bookingsystem.exceptions.InvalidPasswordException;
import com.mycompany.hotel.bookingsystem.models.bookings.Booking;
import com.mycompany.hotel.bookingsystem.models.payment.Payment;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Customer extends User {
    private final List<Booking> bookings;
    private final List<Payment> paymentMethods;

    public Customer(String name, String email, String password)
            throws InvalidEmailException, InvalidPasswordException {
        super(name, email, password);
        this.bookings = new ArrayList<>();
        this.paymentMethods = new ArrayList<>();
    }

    // Constructor without password
    public Customer(String name, String email) throws InvalidEmailException {
        super(name, email, null);
        this.bookings = new ArrayList<>();
        this.paymentMethods = new ArrayList<>();
    }

    public List<Booking> getBookings() {
        return Collections.unmodifiableList(bookings);
    }

    public List<Payment> getPaymentMethods() {
        return Collections.unmodifiableList(paymentMethods);
    }

    public void addBooking(Booking booking) {
        if (booking != null) {
            bookings.add(booking);
        }
    }

    public void addPaymentMethod(Payment method) {
        if (method != null) {
            paymentMethods.add(method);
        }
    }

    @Override
    public boolean login(String enteredPassword) {
        // Customers can login without password if none is set
        if (getPassword() == null) {
            System.out.println(getName() + " (Customer) logged in (no password required).");
            return true;
        }

        // Verify password if one is set
        boolean success = enteredPassword != null && enteredPassword.equals(getPassword());
        System.out.println(getName() + " (Customer) " + (success ? "successfully" : "failed to") + " log in.");
        return success;
    }

    @Override
    public void logout() {
        System.out.println(getName() + " (Customer) logged out.");
    }
}