package com.mycompany.hotel.bookingsystem.controllers;

import com.mycompany.hotel.bookingsystem.App;
import com.mycompany.hotel.bookingsystem.models.Hotel;
import com.mycompany.hotel.bookingsystem.models.rooms.Room;
import com.mycompany.hotel.bookingsystem.views.RoomsView;
import javafx.collections.ObservableList;

import java.util.stream.Collectors;

public class RoomsController {

    private final Hotel hotel;
    private final ObservableList<Room> availableRooms;
    private final RoomsView roomsView;
    private final App mainApp; // Reference to the main application if needed

    public RoomsController(Hotel hotel, ObservableList<Room> availableRooms, RoomsView roomsView, App mainApp) {
        this.hotel = hotel;
        this.availableRooms = availableRooms;
        this.roomsView = roomsView;
        this.mainApp = mainApp;
        initializeController();
    }

    private void initializeController() {
        roomsView.setFilterAction(e -> handleFilterSelection(roomsView.getFilterCombo().getValue()));
        // You can add actions for the "Book Now" button here if you handle it in the controller
        // Or, you can handle it directly in the RoomCardView and communicate with the App.
    }

    private void handleFilterSelection(String selectedType) {
        if (selectedType.equals("All")) {
            updateAvailableRooms();
        } else {
            availableRooms.setAll(hotel.getRooms().stream()
                    .filter(room -> room.getType().equalsIgnoreCase(selectedType) && room.isAvailable())
                    .collect(Collectors.toList()));
        }
    }

    private void updateAvailableRooms() {
        availableRooms.setAll(hotel.getRooms().stream().filter(Room::isAvailable).collect(Collectors.toList()));
    }
}