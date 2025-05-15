package com.mycompany.hotel.bookingsystem.models.rooms;

public class DoubleRoom extends Room {
    public DoubleRoom(int roomNumber, double price) {
        super(roomNumber, "Double", price, 2);
    }
}