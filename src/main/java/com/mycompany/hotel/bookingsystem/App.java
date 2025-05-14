package com.mycompany.hotel.bookingsystem;

import com.mycompany.hotel.bookingsystem.controllers.*;
import com.mycompany.hotel.bookingsystem.exceptions.InvalidCodeException;
import com.mycompany.hotel.bookingsystem.exceptions.InvalidEmailException;
import com.mycompany.hotel.bookingsystem.exceptions.InvalidPasswordException;
import com.mycompany.hotel.bookingsystem.models.*;
import com.mycompany.hotel.bookingsystem.models.bookings.Booking;
import com.mycompany.hotel.bookingsystem.models.offers.Offer;
import com.mycompany.hotel.bookingsystem.models.offers.SeasonalOffer;
import com.mycompany.hotel.bookingsystem.models.offers.SpecialCodeOffer;
import com.mycompany.hotel.bookingsystem.models.payment.CreditCardPayment;
import com.mycompany.hotel.bookingsystem.models.payment.PayPalPayment;
import com.mycompany.hotel.bookingsystem.models.payment.Payment;
import com.mycompany.hotel.bookingsystem.models.reviews.Review;
import com.mycompany.hotel.bookingsystem.models.rooms.DoubleRoom;
import com.mycompany.hotel.bookingsystem.models.rooms.Room;
import com.mycompany.hotel.bookingsystem.models.rooms.SingleRoom;
import com.mycompany.hotel.bookingsystem.models.rooms.SuiteRoom;
import com.mycompany.hotel.bookingsystem.models.users.Customer;
import com.mycompany.hotel.bookingsystem.views.*;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class App extends Application {
    private Hotel hotel;
    private ObservableList<Booking> bookings;
    private ObservableList<Room> availableRooms;
    private ObservableList<Review> reviews;
    private ObservableList<Offer> offers;  // Added for Offers
    private List<SeasonalOffer> seasonalOffers;
    private Stage primaryStage; // Added to store the primary stage

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage; // Store the primary stage
        // Initialize model
        hotel = new Hotel();
        bookings = FXCollections.observableArrayList();
        availableRooms = FXCollections.observableArrayList();
        reviews = FXCollections.observableArrayList();
        offers = FXCollections.observableArrayList(); // Initialize the offers list
        seasonalOffers = new ArrayList<>();

        // Initialize sample data
        initializeSampleData();

        // Create views
        AdminView adminView = new AdminView(availableRooms, bookings, reviews);
        BookingView bookingView = new BookingView(availableRooms);
        RoomsView roomsView = new RoomsView(hotel, availableRooms);
        ServicesView servicesView = new ServicesView();
        ReviewsView reviewsView = new ReviewsView(reviews);
        OffersView offersView = new OffersView(offers); // Pass offers to OffersView

        // Create controllers
        new AdminController(adminView, hotel, availableRooms, bookings, reviews, this);
        new BookingController(bookingView, hotel, bookings, availableRooms, this);
        new RoomsController(hotel, availableRooms, roomsView, this);
        new ServicesController(servicesView, this);
        new ReviewsController(reviewsView, hotel, reviews, this);
        new OffersController(offersView, hotel, offers, this); // Pass offers to OffersController

        // Setup main tab pane
        TabPane tabPane = new TabPane();
        tabPane.getTabs().addAll(
                bookingView.getBookingTab(),
                roomsView.getRoomsTab(),
                servicesView.getServicesTab(),
                reviewsView.getReviewsTab(),
                adminView.getAdminTab()
        );

        // Configure main stage
        Scene scene = new Scene(tabPane, 1000, 700);
        primaryStage.setTitle("Hotel Booking System");
        primaryStage.setScene(scene);
        primaryStage.show();
    }


    private void initializeSampleData() {
        try {
            // Add sample rooms
            hotel.addRoom(new SingleRoom(101, 100.0, true));
            hotel.addRoom(new DoubleRoom(201, 150.0, true));
            hotel.addRoom(new SuiteRoom(301, 250.0, true));
            hotel.addRoom(new SingleRoom(102, 100.0, false));
            hotel.addRoom(new DoubleRoom(202, 150.0, true));

            // Initialize available rooms
            updateAvailableRooms();

            // Add sample reviews
            Customer sampleCustomer = new Customer("John Doe", "john@example.com", "password123");
            reviews.add(new Review(1, sampleCustomer, 5, "Excellent service!", new Date()));
            reviews.add(new Review(2, sampleCustomer, 4, "Very good experience", new Date()));

            // Initialize offers
            SeasonalOffer seasonalOffer1 = new SeasonalOffer(0.1, new Date(System.currentTimeMillis() - 86400000L), new Date(System.currentTimeMillis() + 86400000L * 10));
            try {
                hotel.addOffer(seasonalOffer1);
                offers.add(seasonalOffer1);
            } catch (Hotel.HotelOperationException e) {
                showAlert("Error", "Error adding seasonal offer: " + e.getMessage());
            } catch (Hotel.InvalidOfferException e) {
                showAlert("Error", "Error adding seasonal offer: " + e.getMessage());
            }
            try {
                SpecialCodeOffer.addCode("SUMMER20", 0.2);
                Offer specialCodeOffer = new SpecialCodeOffer("SUMMER20");
                hotel.addOffer(specialCodeOffer);
                offers.add(specialCodeOffer);
            } catch (IllegalArgumentException | IllegalStateException | Hotel.InvalidOfferException |
                     InvalidCodeException e) {
                showAlert("Error", "Error adding special code offer: " + e.getMessage());
            }

        } catch (Exception e) {
            showAlert("Error", "Failed to initialize sample data: " + e.getMessage());
        }
    }

    // Method to update available rooms
    private void updateAvailableRooms() {
        availableRooms.clear();
        try {
            availableRooms.addAll(hotel.searchAvailableRooms());
        } catch (Hotel.HotelOperationException e) {
            showAlert("Error", "Error updating available rooms: " + e.getMessage());
        }
    }

    public void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    //Added showPaymentDialog
    public Payment showPaymentDialog(ToggleGroup paymentGroup) {
        Stage dialog = new Stage();
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.initOwner(primaryStage); // Set the owner
        dialog.setTitle("Payment Information");

        VBox box = new VBox(10);
        box.setPadding(new Insets(20));
        box.setStyle("-fx-background-color: #f5f7fa;");

        Label errorLabel = new Label();
        errorLabel.setTextFill(Color.RED);

        Payment[] payment = new Payment[1];

        if (((RadioButton) paymentGroup.getSelectedToggle()).getText().equals("Credit Card")) {
            // Credit card form
            Label title = new Label("Credit Card Details");
            title.setFont(Font.font("Arial", FontWeight.BOLD, 16));

            TextField cardNumberField = new TextField();
            cardNumberField.setPromptText("Card Number (16 digits)");

            TextField expiryField = new TextField();
            expiryField.setPromptText("MM/YY");

            TextField nameField = new TextField();
            nameField.setPromptText("Cardholder Name");

            TextField cvvField = new TextField();
            cvvField.setPromptText("CVV (3 digits)");

            Button submitButton = new Button("Submit Payment");
            submitButton.setStyle("-fx-background-color: #3498db; -fx-text-fill: white;");

            box.getChildren().addAll(title, cardNumberField, expiryField, nameField, cvvField, errorLabel, submitButton);

            submitButton.setOnAction(e -> {
                String cardNumber = cardNumberField.getText();
                String expiry = expiryField.getText();
                String name = nameField.getText();
                String cvv = cvvField.getText();

                if (cardNumber.length() != 16 || !cardNumber.matches("\\d+")) {
                    errorLabel.setText("Invalid card number.");
                    return;
                }
                if (!expiry.matches("(0[1-9]|1[0-2])\\/\\d{2}") || expiry.length() != 5) {
                    errorLabel.setText("Invalid expiry format (MM/YY).");
                    return;
                }
                if (name.isEmpty()) {
                    errorLabel.setText("Please enter cardholder name.");
                    return;
                }
                if (cvv.length() != 3 || !cvv.matches("\\d+")) {
                    errorLabel.setText("Invalid CVV.");
                    return;
                }

                payment[0] = new CreditCardPayment(cardNumber, new Date(), name, cvv); // Dummy Date
                dialog.close();
            });
        } else if (((RadioButton) paymentGroup.getSelectedToggle()).getText().equals("PayPal")) {
            // PayPal form
            Label title = new Label("PayPal Details");
            title.setFont(Font.font("Arial", FontWeight.BOLD, 16));
            TextField emailField = new TextField();
            emailField.setPromptText("PayPal Email");
            Button submitButton = new Button("Submit Payment");
            submitButton.setStyle("-fx-background-color: #ffc85e; -fx-text-fill: #003087;");

            box.getChildren().addAll(title, emailField, errorLabel, submitButton);

            submitButton.setOnAction(e -> {
                String email = emailField.getText();
                if (!email.matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$")) {
                    errorLabel.setText("Invalid email address.");
                    return;
                }
                try {
                    payment[0] = new PayPalPayment(email);
                } catch (InvalidEmailException ex) {
                    throw new RuntimeException(ex);
                }
                dialog.close();
            });
        }

        Scene scene = new Scene(box);
        dialog.setScene(scene);
        dialog.showAndWait();

        return payment[0];
    }

    public static void main(String[] args) {
        launch();
    }
}
