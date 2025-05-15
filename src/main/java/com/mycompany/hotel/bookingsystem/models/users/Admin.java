package com.mycompany.hotel.bookingsystem.models.users;

import com.mycompany.hotel.bookingsystem.exceptions.InvalidEmailException;
import com.mycompany.hotel.bookingsystem.exceptions.InvalidPasswordException;

public class Admin extends User {
    public Admin(String name, String email, String password)
            throws InvalidEmailException, InvalidPasswordException {
        super(name, email, validateAdminPassword(password));
    }

    private static String validateAdminPassword(String password) throws InvalidPasswordException {
        if (password == null || password.length() < 8) {
            throw new InvalidPasswordException("Admin password must be at least 8 characters");
        }
        return password;
    }

    @Override
    public boolean login(String enteredPassword) {
        boolean success = enteredPassword != null && enteredPassword.equals(getPassword());
        System.out.println(getName() + " (Admin) " + (success ? "successfully" : "failed to") + " log in.");
        return success;
    }

    @Override
    public void logout() {
        System.out.println(getName() + " (Admin) logged out.");
    }
}