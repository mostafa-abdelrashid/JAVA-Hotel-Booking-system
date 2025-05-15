package com.mycompany.hotel.bookingsystem.models.rooms;

import java.util.Objects;

public abstract class Room {
    private final int roomNumber;
    private final String type;
    private double price;
    private boolean isAvailable;
    private final int capacity;

    protected Room(int roomNumber, String type, double price, int capacity) {
        this.roomNumber = validateRoomNumber(roomNumber);
        this.type = validateType(type);
        this.price = validatePrice(price);
        this.capacity = validateCapacity(capacity);
        this.isAvailable = true; // Default to available
    }

    // Validation helpers
    private static int validateRoomNumber(int number) {
        if (number <= 0) throw new IllegalArgumentException("Room number must be positive");
        return number;
    }

    private static String validateType(String type) {
        if (type == null || type.trim().isEmpty()) {
            throw new IllegalArgumentException("Room type cannot be empty");
        }
        return type.trim();
    }

    private static double validatePrice(double price) {
        if (price < 0) throw new IllegalArgumentException("Price cannot be negative");
        return price;
    }

    private static int validateCapacity(int capacity) {
        if (capacity <= 0) throw new IllegalArgumentException("Capacity must be positive");
        return capacity;
    }

    // Domain methods
    public void book() {
        if (!isAvailable) {
            throw new IllegalStateException("Room #" + roomNumber + " is already booked");
        }
        this.isAvailable = false;
    }

    public void checkout() {
        if (isAvailable) {
            throw new IllegalStateException("Room #" + roomNumber + " is not currently booked");
        }
        this.isAvailable = true;
    }

    // Getters
    public int getRoomNumber() { return roomNumber; }
    public String getType() { return type; }
    public double getPrice() { return price; }
    public boolean isAvailable() { return isAvailable; }
    public int getCapacity() { return capacity; }

    // Setters
    public void setPrice(double price) {
        this.price = validatePrice(price);
    }

    public int compareTo(Room other) {
        return Double.compare(this.price, other.price);
    }

    @Override
    public String toString() {
        return String.format("Room #%d - %s - $%.2f - %s - Capacity: %d",
                roomNumber,
                type,
                price,
                isAvailable ? "Available" : "Occupied",
                capacity);
    }
}