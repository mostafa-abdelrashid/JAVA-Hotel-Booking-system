
package com.mycompany.hotel.bookingsystem.models.users;


import com.mycompany.hotel.bookingsystem.exceptions.InvalidEmailException;
import com.mycompany.hotel.bookingsystem.exceptions.InvalidPasswordException;

public abstract class User {
    private final int id;  // made final since ID shouldn't change
    private static int idCounter = 1;
    private String name;
    private String email;
    private String password;

    // Constructor with validation
    public User(String name, String email, String password)
            throws InvalidEmailException, InvalidPasswordException {
        this.id = idCounter++;
        setName(name);
        setEmail(email);  // using setter for validation
        setPassword(password);
    }

    // Getters
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    protected String getPassword() {
        return password;
    }

    // Setters with validation
    public void setName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Name cannot be null or empty");
        }
        this.name = name;
    }

    public void setEmail(String email) throws InvalidEmailException {
        if (email == null ||   email.trim().isEmpty() || !email.contains(".") || !email.contains("@") )  {
            throw new InvalidEmailException("Invalid email format: " + email);
        }
        this.email = email;
    }

    public void setPassword(String password) throws InvalidPasswordException {
        if (password == null || password.length() < 8) {
            throw new InvalidPasswordException("Password must be at least 8 characters");
        }
        this.password = password;
    }

    // Abstract methods
    public abstract boolean login(String enteredPassword);
    public abstract void logout();
}

