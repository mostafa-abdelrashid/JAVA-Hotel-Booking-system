package com.mycompany.hotel.bookingsystem.models.rooms;

public class SuiteRoom extends Room {
    public SuiteRoom(int roomNumber, double price) {
        super(roomNumber, "Suite", price, 4);
    }
}
