package com.mycompany.hotel.bookingsystem.controllers;

import com.mycompany.hotel.bookingsystem.App;
import com.mycompany.hotel.bookingsystem.models.bookings.Booking;
import com.mycompany.hotel.bookingsystem.models.Hotel;
import com.mycompany.hotel.bookingsystem.models.reviews.Review;
import com.mycompany.hotel.bookingsystem.models.rooms.Room;
import com.mycompany.hotel.bookingsystem.models.rooms.SingleRoom;
import com.mycompany.hotel.bookingsystem.views.AdminView;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.TextInputDialog;
import java.util.Optional;
import java.util.Date;


public class AdminController {

    private final AdminView adminView;
    private final Hotel hotel;
    private final ObservableList<Room> rooms;
    private final ObservableList<Booking> bookings;
    private final ObservableList<Review> reviews;
    private final App mainApp;

    public AdminController(AdminView adminView, Hotel hotel, ObservableList<Room> rooms, ObservableList<Booking> bookings, ObservableList<Review> reviews, App mainApp) {
        this.adminView = adminView;
        this.hotel = hotel;
        this.rooms = rooms;
        this.bookings = bookings;
        this.reviews = reviews;
        this.mainApp = mainApp;
        initializeController();
    }

    private void initializeController() {
        adminView.getAddRoomButton().setOnAction(e -> handleAddRoom());
        adminView.getUpdateRoomButton().setOnAction(e -> handleUpdateRoom());
        adminView.getDeleteRoomButton().setOnAction(e -> {
            try {
                handleDeleteRoom();
            } catch (Hotel.RoomNotFoundException ex) {
                throw new RuntimeException(ex);
            } catch (Hotel.HotelOperationException ex) {
                throw new RuntimeException(ex);
            }
        });
        adminView.getUpdateBookingButton().setOnAction(e -> handleUpdateBooking());
        adminView.getDeleteBookingButton().setOnAction(e -> {
            try {
                handleDeleteBooking();
            } catch (Hotel.HotelOperationException ex) {
                throw new RuntimeException(ex);
            }
        });
        adminView.getDeleteReviewButton().setOnAction(e -> {
            try {
                handleDeleteReview();
            } catch (Hotel.HotelOperationException ex) {
                throw new RuntimeException(ex);
            }
        });
    }

    private void handleAddRoom() {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Add Room");
        dialog.setHeaderText("Enter Room Details");
        dialog.setContentText("Room Number:");

        Optional<String> result = dialog.showAndWait();
        result.ifPresent(roomNumber -> {
            try {
                int number = Integer.parseInt(roomNumber);
                // For simplicity, let's create a SingleRoom.  You'd need a dialog to choose the room type.
                Room newRoom = new SingleRoom(number, 100.0, true);  // Dummy price
                hotel.addRoom(newRoom);
                rooms.add(newRoom);
                mainApp.showAlert("Success", "Room added successfully.");
            } catch (NumberFormatException e) {
                mainApp.showAlert("Error", "Invalid room number. Please enter a valid number.");
            } catch (Hotel.InvalidRoomException e) {
                throw new RuntimeException(e);
            } catch (Hotel.HotelOperationException e) {
                throw new RuntimeException(e);
            }
        });
    }

    private void handleUpdateRoom() {
        Room selectedRoom = adminView.getRoomListView().getSelectionModel().getSelectedItem();
        if (selectedRoom == null) {
            mainApp.showAlert("Error", "Please select a room to update.");
            return;
        }

        // For simplicity, let's just toggle availability.  A real update would need a dialog.
        selectedRoom.setAvailable(!selectedRoom.isAvailable());
        adminView.getRoomListView().refresh(); // Update the view
        mainApp.showAlert("Success", "Room availability updated.");
    }

    private void handleDeleteRoom() throws Hotel.RoomNotFoundException, Hotel.HotelOperationException {
        Room selectedRoom = adminView.getRoomListView().getSelectionModel().getSelectedItem();
        if (selectedRoom == null) {
            mainApp.showAlert("Error", "Please select a room to delete.");
            return;
        }

        hotel.removeRoom(selectedRoom);
        rooms.remove(selectedRoom);
        mainApp.showAlert("Success", "Room deleted successfully.");
    }

    private void handleUpdateBooking() {
        Booking selectedBooking = adminView.getBookingListView().getSelectionModel().getSelectedItem();
        if (selectedBooking == null) {
            mainApp.showAlert("Error", "Please select a booking to update.");
            return;
        }
        // In a real application, you'd present a dialog to modify booking details.
        // For simplicity, we'll just print a message.
        System.out.println("Updating booking: " + selectedBooking.getBookingId());
        mainApp.showAlert("Info", "Booking update functionality not implemented in this example.");
    }

    private void handleDeleteBooking() throws Hotel.HotelOperationException {
        Booking selectedBooking = adminView.getBookingListView().getSelectionModel().getSelectedItem();
        if (selectedBooking == null) {
            mainApp.showAlert("Error", "Please select a booking to delete.");
            return;
        }

        hotel.removeBooking(selectedBooking); // Corrected line
        bookings.remove(selectedBooking);
        mainApp.showAlert("Success", "Booking deleted successfully.");
    }

    private void handleDeleteReview() throws Hotel.HotelOperationException {
        Review selectedReview = adminView.getReviewListView().getSelectionModel().getSelectedItem();
        if (selectedReview == null) {
            mainApp.showAlert("Error", "Please select a review to delete.");
            return;
        }

        hotel.removeReview(selectedReview); // Corrected line
        reviews.remove(selectedReview);
        mainApp.showAlert("Success", "Review deleted successfully.");
    }
}
