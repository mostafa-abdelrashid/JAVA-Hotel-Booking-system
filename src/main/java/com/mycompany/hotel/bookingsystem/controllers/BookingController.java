package com.mycompany.hotel.bookingsystem.controllers;

import com.mycompany.hotel.bookingsystem.App;
import com.mycompany.hotel.bookingsystem.exceptions.InvalidEmailException;
import com.mycompany.hotel.bookingsystem.exceptions.InvalidPasswordException;
import com.mycompany.hotel.bookingsystem.models.bookings.Booking;
import com.mycompany.hotel.bookingsystem.models.users.Customer;
import com.mycompany.hotel.bookingsystem.models.Hotel;
import com.mycompany.hotel.bookingsystem.models.payment.Payment;
import com.mycompany.hotel.bookingsystem.models.rooms.Room;
import com.mycompany.hotel.bookingsystem.views.BookingView;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

public class BookingController {

    private final BookingView bookingView;
    private final Hotel hotel;
    private final ObservableList<Booking> bookings;
    private final ObservableList<Room> availableRooms;
    private final App mainApp;

    public BookingController(BookingView bookingView, Hotel hotel, ObservableList<Booking> bookings, ObservableList<Room> availableRooms, App mainApp) {
        this.bookingView = bookingView;
        this.hotel = hotel;
        this.bookings = bookings;
        this.availableRooms = availableRooms;
        this.mainApp = mainApp;
        initializeController();
    }

    private void initializeController() {
        bookingView.getBookRoomButton().setOnAction(e -> handleBookingSubmission());
        // You might want to add listeners to the date pickers to update available rooms
    }

    private void handleBookingSubmission() {
        LocalDate checkInDate = bookingView.getCheckInDatePicker().getValue();
        LocalDate checkOutDate = bookingView.getCheckOutDatePicker().getValue();
        Room selectedRoom = bookingView.getRoomComboBox().getValue();
        String customerName = bookingView.getCustomerNameField().getText();
        String customerEmail = bookingView.getCustomerEmailField().getText();
        ToggleGroup paymentGroup = bookingView.getPaymentGroup();

        if (checkInDate == null || checkOutDate == null || selectedRoom == null || customerName.isEmpty() || customerEmail.isEmpty() || paymentGroup.getSelectedToggle() == null) {
            mainApp.showAlert("Error", "Please fill in all the booking details.");
            return;
        }

        if (checkInDate.isAfter(checkOutDate) || checkInDate.isEqual(checkOutDate)) {
            mainApp.showAlert("Error", "Check-out date must be after check-in date.");
            return;
        }

        try {
            //  customer.
            Customer customer = new Customer(customerName, customerEmail, "password"); //  password

            // Create the booking
            Booking booking = new Booking(
                    bookings.size() + 1, //  ID
                    customer,
                    selectedRoom,
                    Date.from(checkInDate.atStartOfDay(ZoneId.systemDefault()).toInstant()),
                    Date.from(checkOutDate.atStartOfDay(ZoneId.systemDefault()).toInstant())
            );

            // Process payment (show payment dialog)
            Payment payment = mainApp.showPaymentDialog(paymentGroup);

            if (payment != null) {
                bookings.add(booking);
                selectedRoom.setAvailable(false); // Mark the room as booked
                updateAvailableRooms(); // Update the list of available rooms

                bookingView.getBookingListView().getItems().add(booking); //add to the listview
                clearBookingForm();
            } else {
                mainApp.showAlert("Payment Cancelled", "Payment was not processed. Booking cancelled.");
            }
        } catch (Booking.InvalidBookingException e) {
            mainApp.showAlert("Booking Error", e.getMessage());
        } catch (InvalidEmailException e) {
            mainApp.showAlert("Invalid Email", e.getMessage());
        } catch (InvalidPasswordException e) {
            mainApp.showAlert("Invalid Password", e.getMessage());
        }
    }

    private void updateAvailableRooms() {
        availableRooms.setAll(hotel.getRooms().stream().filter(Room::isAvailable).collect(java.util.stream.Collectors.toList()));
    }

    private void clearBookingForm() {
        bookingView.getCheckInDatePicker().setValue(null);
        bookingView.getCheckOutDatePicker().setValue(null);
        bookingView.getRoomComboBox().setValue(null);
        bookingView.getCustomerNameField().clear();
        bookingView.getCustomerEmailField().clear();
        bookingView.getPaymentGroup().selectToggle(null);
    }
}

