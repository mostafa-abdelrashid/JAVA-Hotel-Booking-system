package com.mycompany.hotel.bookingsystem.models.rooms;

public class DoubleRoom extends Room {
    private static final int CAPACITY = 2;

    public DoubleRoom(int roomNumber, double price, boolean isAvailable) {
        super(roomNumber, "Double", price, isAvailable, CAPACITY);
    }
}
