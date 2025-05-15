package com.mycompany.hotel.bookingsystem.models.rooms;

public class SingleRoom extends Room {
    public SingleRoom(int roomNumber, double price) {
        super(roomNumber, "Single", price, 1);
    }
}
