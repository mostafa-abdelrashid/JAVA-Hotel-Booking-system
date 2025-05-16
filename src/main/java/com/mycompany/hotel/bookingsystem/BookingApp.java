package com.mycompany.hotel.bookingsystem;

import com.mycompany.hotel.bookingsystem.controllers.BookingController;
import com.mycompany.hotel.bookingsystem.exceptions.InvalidServiceException;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

import java.sql.Date;
import java.time.LocalDate;
import java.util.*;
import com.mycompany.hotel.bookingsystem.models.rooms.Room; //Import the Room class

public class BookingApp extends Application {
    private BookingController controller;
    private ListView<String> bookingsView;
    private VBox creditCardFields;
    private VBox paypalFields;
    private TextField offerCodeField;
    private TextField cardNumberField;
    private TextField expiryField;
    private TextField cvvField;
    private TextField holderField;
    private TextField paypalEmailField;

    // Service-related UI elements
    private CheckBox laundryCheckBox;
    private TextField laundryCountField;
    private CheckBox roomServiceCheckBox;
    private ComboBox<String> mealTypeCombo;
    private CheckBox spaServiceCheckBox;
    private ComboBox<String> spaPackageCombo;
    private ListView<String> availableRoomsView; // Add this ListView
    private ComboBox<String> roomTypeComboBox; // Changed name for clarity

    public BookingApp() throws InvalidServiceException {
        this.controller = new BookingController();
    }

    private static Date convertToDate(LocalDate localDate) {
        return (localDate != null) ? Date.valueOf(localDate) : null;
    }

    @Override
    public void start(Stage stage) {
        SplitPane mainLayout = new SplitPane();
        mainLayout.setDividerPositions(0.6);

        // FORM SECTION
        VBox formSection = new VBox(15);
        formSection.setPadding(new Insets(20));
        formSection.setStyle("-fx-background-color: #f5f5f5;");

        ScrollPane formScrollPane = new ScrollPane();
        formScrollPane.setContent(formSection);
        formScrollPane.setFitToWidth(true);
        formScrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        formScrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);

        // Title
        Label title = new Label("Hotel Booking System");
        title.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        title.setTextFill(Color.DARKBLUE);

        // Customer Information - Side by side
        Label customerLabel = new Label("Customer Information:");
        customerLabel.setFont(Font.font("Arial", FontWeight.BOLD, 14));

        HBox nameEmailBox = new HBox(15);
        VBox nameBox = new VBox(5);
        Label nameLabel = new Label("Full Name:");
        TextField nameField = new TextField();
        nameBox.getChildren().addAll(nameLabel, nameField);

        VBox emailBox = new VBox(5);
        Label emailLabel = new Label("Email:");
        TextField emailField = new TextField();
        emailBox.getChildren().addAll(emailLabel, emailField);

        nameEmailBox.getChildren().addAll(nameBox, emailBox);

        // Booking Details - Side by side
        Label bookingLabel = new Label("Booking Details:");
        bookingLabel.setFont(Font.font("Arial", FontWeight.BOLD, 14));

        HBox dateBox = new HBox(15);
        VBox checkInBox = new VBox(5);
        Label checkInLabel = new Label("Check-in Date:");
        DatePicker checkInDate = new DatePicker();
        checkInBox.getChildren().addAll(checkInLabel, checkInDate);

        VBox checkOutBox = new VBox(5);
        Label checkOutLabel = new Label("Check-out Date:");
        DatePicker checkOutDate = new DatePicker();
        checkOutBox.getChildren().addAll(checkOutLabel, checkOutDate);

        dateBox.getChildren().addAll(checkInBox, checkOutBox);

        // Room Type
        Label roomLabel = new Label("Room Type:");
        // Use the new ComboBox, but populate it later
        roomTypeComboBox = new ComboBox<>();
        roomTypeComboBox.setPromptText("Select Room Type");

        // Services Selection
        Label servicesLabel = new Label("Select Services:");
        servicesLabel.setFont(Font.font("Arial", FontWeight.BOLD, 14));

        GridPane servicesGrid = new GridPane();
        servicesGrid.setHgap(10);
        servicesGrid.setVgap(5);

        // Laundry Service
        laundryCheckBox = new CheckBox("Laundry Service");
        laundryCountField = new TextField();
        laundryCountField.setPromptText("Clothes Count");
        laundryCountField.setDisable(true);
        laundryCheckBox.setOnAction(e -> laundryCountField.setDisable(!laundryCheckBox.isSelected()));
        servicesGrid.add(laundryCheckBox, 0, 0);
        servicesGrid.add(laundryCountField, 1, 0);

        // Room Service
        roomServiceCheckBox = new CheckBox("Room Service");
        mealTypeCombo = new ComboBox<>(FXCollections.observableArrayList("Breakfast", "Dinner")); // Meal type dropdown
        mealTypeCombo.setPromptText("Select Meal");
        mealTypeCombo.setDisable(true);
        roomServiceCheckBox.setOnAction(e -> mealTypeCombo.setDisable(!roomServiceCheckBox.isSelected()));
        servicesGrid.add(roomServiceCheckBox, 0, 1);
        servicesGrid.add(mealTypeCombo, 1, 1); // Added mealTypeCombo to the grid

        // Spa Service
        spaServiceCheckBox = new CheckBox("Spa Service");
        spaPackageCombo = new ComboBox<>(FXCollections.observableArrayList("Basic", "Premium"));  // Spa package dropdown
        spaPackageCombo.setPromptText("Select Package");
        spaPackageCombo.setDisable(true);
        spaServiceCheckBox.setOnAction(e -> spaPackageCombo.setDisable(!spaServiceCheckBox.isSelected()));
        servicesGrid.add(spaServiceCheckBox, 0, 2);
        servicesGrid.add(spaPackageCombo, 1, 2); // Added spaPackageCombo to the grid

        // Offer Code Input
        Label offerCodeLabel = new Label("Offer Code:");
        offerCodeLabel.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        offerCodeField = new TextField();
        offerCodeField.setPromptText("Enter offer code");

        // Payment Method
        Label paymentLabel = new Label("Payment Method:");
        paymentLabel.setFont(Font.font("Arial", FontWeight.BOLD, 14));

        ToggleGroup paymentGroup = new ToggleGroup();
        RadioButton creditCardOption = new RadioButton("Credit Card");
        RadioButton paypalOption = new RadioButton("PayPal");
        paypalOption.setSelected(true); // Default to PayPal
        creditCardOption.setToggleGroup(paymentGroup);
        paypalOption.setToggleGroup(paymentGroup);

        HBox paymentOptions = new HBox(15, creditCardOption, paypalOption);

        // Payment Fields Container
        StackPane paymentFieldsContainer = new StackPane();
        creditCardFields = createCreditCardFields();
        paypalFields = createPaypalFields();
        paymentFieldsContainer.getChildren().addAll(creditCardFields, paypalFields);

        // Set initial visibility based on default selection
        creditCardFields.setVisible(!paypalOption.isSelected());
        paypalFields.setVisible(paypalOption.isSelected());

        // Toggle payment fields
        paymentGroup.selectedToggleProperty().addListener((obs, oldVal, newVal) -> {
            boolean isCreditCard = newVal == creditCardOption;
            creditCardFields.setVisible(isCreditCard);
            paypalFields.setVisible(!isCreditCard);
        });

        // Submit Button
        Button submitBtn = new Button("Submit Booking");
        submitBtn.setStyle("-fx-background-color: #4a7bed; -fx-text-fill: white; -fx-font-weight: bold;");
        submitBtn.setMaxWidth(Double.MAX_VALUE);

        submitBtn.setOnAction(e -> {
            // Collect selected services and their values
            Map<Integer, String> selectedServicesWithValues = new HashMap<>();
            String email = emailField.getText().trim();

            if (laundryCheckBox.isSelected()) {
                selectedServicesWithValues.put(3, laundryCountField.getText());
            }
            if (roomServiceCheckBox.isSelected()) {
                selectedServicesWithValues.put(1, mealTypeCombo.getValue()); // Get selected meal type
            }
            if (spaServiceCheckBox.isSelected()) {
                selectedServicesWithValues.put(5, spaPackageCombo.getValue()); // Get selected spa package
            }

            String offerCode = offerCodeField.getText().trim();

            String output = controller.processBooking(
                    nameField.getText(),
                    emailField.getText(),
                    convertToDate(checkInDate.getValue()),
                    convertToDate(checkOutDate.getValue()),
                    roomTypeComboBox.getValue(), // Get selected room type from ComboBox
                    selectedServicesWithValues,
                    offerCode,
                    creditCardOption.isSelected(),
                    cardNumberField.getText(),
                    expiryField.getText(),
                    holderField.getText(),
                    cvvField.getText(),
                    paypalEmailField.getText()
            );
            updateBookingsView(output);
            showAvailableRooms(); // REFRESH THE LIST
        });

        // Form Layout
        formSection.getChildren().addAll(
                title,
                customerLabel,
                nameEmailBox,
                bookingLabel,
                dateBox,
                roomLabel, roomTypeComboBox, // Use the ComboBox
                servicesLabel,
                servicesGrid,
                offerCodeLabel,
                offerCodeField,
                paymentLabel,
                paymentOptions,
                paymentFieldsContainer,
                submitBtn
        );

        // BOOKINGS VIEW SECTION
        VBox bookingsSection = new VBox(15);
        bookingsSection.setPadding(new Insets(20));
        bookingsSection.setStyle("-fx-background-color: #e9e9e9;");

        Label bookingsTitle = new Label("Your Bookings");
        bookingsTitle.setFont(Font.font("Arial", FontWeight.BOLD, 18));

        bookingsView = new ListView<>();
        bookingsView.setPlaceholder(new Label("No bookings yet"));

        Button refreshBtn = new Button("Refresh Bookings");
        refreshBtn.setOnAction(e -> updateBookingsView(""));

        // Add a section for displaying available rooms
        Label availableRoomsTitle = new Label("Available Rooms");
        availableRoomsTitle.setFont(Font.font("Arial", FontWeight.BOLD, 18));
        availableRoomsView = new ListView<>();
        availableRoomsView.setPlaceholder(new Label("No rooms available"));

        bookingsSection.getChildren().addAll(
                bookingsTitle,
                refreshBtn,
                bookingsView,
                availableRoomsTitle, //Addded
                availableRoomsView // Add the available rooms ListView
        );

        mainLayout.getItems().addAll(formScrollPane, bookingsSection);

        Scene scene = new Scene(mainLayout, 900, 700);
        stage.setTitle("Hotel Booking System");
        stage.setScene(scene);
        stage.show();

        //Populate the room list and show available rooms on startup
        populateRoomComboBox();
        showAvailableRooms();
    }

    private void populateRoomComboBox() {
        List<Room> rooms = controller.getAvailableRooms();
        ObservableList<String> roomTypes = FXCollections.observableArrayList();
        for (Room room : rooms) {
            String roomType = room.getClass().getSimpleName();
            if (!roomTypes.contains(roomType)) { //avoid duplicates
                roomTypes.add(roomType);
            }
        }
        roomTypeComboBox.setItems(roomTypes);
        if (!roomTypes.isEmpty()) {
            roomTypeComboBox.setValue(roomTypes.get(0)); // Optionally select the first room type
        }
    }

    private void showAvailableRooms() {
        List<Room> rooms = controller.getAvailableRooms();
        Collections.sort(rooms);
        availableRoomsView.getItems().clear(); // Clear old items
        for (Room room : rooms) {
            availableRoomsView.getItems().add(room.toString());
        }
    }


    private VBox createCreditCardFields() {
        VBox fields = new VBox(5);

        cardNumberField = new TextField();
        cardNumberField.setPromptText("Card Number");
        expiryField = new TextField();
        expiryField.setPromptText("MM/YY");
        cvvField = new TextField();
        cvvField.setPromptText("CVV");
        holderField = new TextField();
        holderField.setPromptText("Cardholder Name");

        fields.getChildren().addAll(
                new Label("Card Number:"), cardNumberField,
                new Label("Expiry Date:"), expiryField,
                new Label("CVV:"), cvvField,
                new Label("Cardholder Name:"), holderField
        );

        return fields;
    }

    private VBox createPaypalFields() {
        VBox fields = new VBox(5);

        paypalEmailField = new TextField();
        paypalEmailField.setPromptText("PayPal Email");

        fields.getChildren().addAll(
                new Label("PayPal Email:"), paypalEmailField
        );

        return fields;
    }

    private void updateBookingsView(String bookingResult) {
        if (bookingResult.startsWith("✅")) {
            String bookingId = bookingResult.split("\n")[1].split(": ")[1];
            String customer = bookingResult.split("\n")[2].split(": ")[1];
            String room = bookingResult.split("\n")[3].split(": ")[1];
            String price = bookingResult.split("Total Price: \\$")[1];

            String entry = String.format("Booking ID: %s | %s | %s | $%s",
                    bookingId, customer, room, price);
            bookingsView.getItems().add(entry);
        } else if (bookingResult.startsWith("⚠️")) {
            // Handle error messages (e.g., invalid offer code)
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Booking Error");
            alert.setHeaderText(null);
            alert.setContentText(bookingResult.substring(3)); // Remove the "⚠️ " prefix
            alert.showAndWait();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
