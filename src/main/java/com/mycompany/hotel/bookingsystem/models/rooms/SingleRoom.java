package com.mycompany.hotel.bookingsystem.models.rooms;

public class SingleRoom extends Room {
    private static final int CAPACITY = 1;

    public SingleRoom(int roomNumber, double price, boolean isAvailable) {
        super(roomNumber, "Single", price, isAvailable, CAPACITY);
    }
}
