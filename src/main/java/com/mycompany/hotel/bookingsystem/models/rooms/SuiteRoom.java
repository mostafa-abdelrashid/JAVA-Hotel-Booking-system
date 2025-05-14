package com.mycompany.hotel.bookingsystem.models.rooms;

public class SuiteRoom extends Room {
    private static final int CAPACITY = 4;

    public SuiteRoom(int roomNumber, double price, boolean isAvailable) {
        super(roomNumber, "Suite", price, isAvailable, CAPACITY);
    }
}
