package com.mycompany.hotel.bookingsystem.views;

import com.mycompany.hotel.bookingsystem.models.bookings.Booking;
import com.mycompany.hotel.bookingsystem.models.rooms.Room;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

import java.time.LocalDate;
import java.util.Date;
import java.util.stream.Collectors;

public class BookingView {

    private VBox view;
    private DatePicker checkInDatePicker;
    private DatePicker checkOutDatePicker;
    private ComboBox<Room> roomComboBox;
    private Button bookRoomButton;
    private ListView<Booking> bookingListView;
    private Tab bookingTab;
    private TextField customerNameField;
    private TextField customerEmailField;
    private ToggleGroup paymentGroup;

    public BookingView(ObservableList<Room> availableRooms) {
        initializeView(availableRooms);
    }

    private void initializeView(ObservableList<Room> availableRooms) {
        VBox formLayout = new VBox(20);
        formLayout.setPadding(new Insets(20));

        Label title = new Label("Book a Room");
        title.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        title.setTextFill(Color.web("#00838f"));

        Label roomLabel = new Label("Select Room:");
        roomComboBox = new ComboBox<>(availableRooms);
        roomComboBox.setPromptText("Available Rooms");
        roomComboBox.setStyle("-fx-background-color: white;");

        Label checkInLabel = new Label("Check-in Date:");
        checkInDatePicker = new DatePicker();
        checkInDatePicker.setPromptText("Select check-in date");
        checkInDatePicker.setStyle("-fx-background-color: white;");

        Label checkOutLabel = new Label("Check-out Date:");
        checkOutDatePicker = new DatePicker();
        checkOutDatePicker.setPromptText("Select check-out date");
        checkOutDatePicker.setStyle("-fx-background-color: white;");

        Label nameLabel = new Label("Your Name:");
        customerNameField = new TextField();
        customerNameField.setPromptText("Enter your name");

        Label emailLabel = new Label("Your Email:");
        customerEmailField = new TextField();
        customerEmailField.setPromptText("Enter your email");

        Label paymentLabel = new Label("Payment Method:");
        paymentGroup = new ToggleGroup();
        RadioButton creditCardRadio = new RadioButton("Credit Card");
        creditCardRadio.setToggleGroup(paymentGroup);
        RadioButton paypalRadio = new RadioButton("PayPal");
        paypalRadio.setToggleGroup(paymentGroup);
        HBox paymentBox = new HBox(10, creditCardRadio, paypalRadio);

        bookRoomButton = new Button("Book Room");
        bookRoomButton.setStyle("-fx-background-color: #00838f; -fx-text-fill: white;");

        bookingListView = new ListView<>();
        bookingListView.setPrefHeight(Region.USE_COMPUTED_SIZE); // Replace fixed height        bookingListView.setStyle("-fx-background-color: white;");
        VBox bookingListLayout = new VBox(20);
        bookingListLayout.setPadding(new Insets(20));

        Label bookingsTitleLabel = new Label("Bookings");
        bookingsTitleLabel.setFont(Font.font("Arial", FontWeight.BOLD, 16));  // Added style
        bookingsTitleLabel.setTextFill(Color.web("#00838f"));
        bookingListLayout.getChildren().addAll(bookingsTitleLabel, bookingListView);

        formLayout.getChildren().addAll(title, roomLabel, roomComboBox, checkInLabel, checkInDatePicker, checkOutLabel, checkOutDatePicker, nameLabel, customerNameField, emailLabel, customerEmailField, paymentLabel, paymentBox, bookRoomButton);

        HBox mainLayout = new HBox(20);
        mainLayout.getChildren().addAll(formLayout, bookingListLayout);
        mainLayout.setStyle("-fx-background-color: linear-gradient(to bottom, #b2ebf2, #80deea);");
        this.view = new VBox();
        this.view.getChildren().add(mainLayout);
        VBox.setVgrow(mainLayout, Priority.ALWAYS); // Add this line

        // Create Tab and set content
        bookingTab = new Tab("Booking", this.view);
        bookingTab.setClosable(false);
    }

    public VBox getView() {
        return view;
    }

    public ComboBox<Room> getRoomComboBox() {
        return roomComboBox;
    }

    public DatePicker getCheckInDatePicker() {
        return checkInDatePicker;
    }

    public DatePicker getCheckOutDatePicker() {
        return checkOutDatePicker;
    }

    public Button getBookRoomButton() {
        return bookRoomButton;
    }

    public ListView<Booking> getBookingListView() {
        return bookingListView;
    }

    public Tab getBookingTab() {
        return bookingTab;
    }

    public TextField getCustomerNameField() {
        return customerNameField;
    }

    public TextField getCustomerEmailField() {
        return customerEmailField;
    }

    public ToggleGroup getPaymentGroup() {
        return paymentGroup;
    }
}

