package com.mycompany.hotel.bookingsystem.models.users;

import com.mycompany.hotel.bookingsystem.exceptions.InvalidEmailException;
import com.mycompany.hotel.bookingsystem.exceptions.InvalidPasswordException;

import java.util.Arrays;

public abstract class User {
    private final int id;
    private static int idCounter = 1;
    private final String name;
    private final String email;
    private final String password;  // Null for non-authenticated users

    // Protected constructor for subclasses to handle password properly
    protected User(String name, String email, String password)
            throws InvalidEmailException {
        this.id = idCounter++;
        this.name = validateName(name);
        this.email = validateEmail(email);
        this.password = password;  // Let subclasses handle validation
    }

    private String validateName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Name cannot be null or empty");
        }
        return name.trim();
    }

    private String validateEmail(String email) throws InvalidEmailException {
        System.out.println("Original email: " + Arrays.toString(email.getBytes())); // Show raw bytes

        String trimmed = email.trim();
        System.out.println("Trimmed email: " + Arrays.toString(trimmed.getBytes()));
        System.out.println("Validating email: '" + email + "'"); // Debug log
        if (email == null) {
            throw new InvalidEmailException("Email cannot be null");
        }

        if (trimmed.isEmpty()) {
            throw new InvalidEmailException("Email cannot be empty");
        }

        // Most basic check - just look for @
        if (!trimmed.contains("@")) {
            throw new InvalidEmailException("Email must contain @");
        }

        return trimmed;
    }

    public final int getId() { return id; }
    public final String getName() { return name; }
    public final String getEmail() { return email; }
    protected final String getPassword() { return password; }

    public abstract boolean login(String enteredPassword);
    public abstract void logout();
}