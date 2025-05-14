package com.mycompany.hotel.bookingsystem.views;

import com.mycompany.hotel.bookingsystem.models.bookings.Booking;
import com.mycompany.hotel.bookingsystem.models.reviews.Review;
import com.mycompany.hotel.bookingsystem.models.rooms.Room;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class AdminView {

    private VBox view;
    private TabPane adminTabPane;

    // Room Management
    private ListView<Room> roomListView;
    private Button addRoomButton;
    private Button updateRoomButton;
    private Button deleteRoomButton;

    // Booking Management
    private ListView<Booking> bookingListView;
    private Button updateBookingButton;
    private Button deleteBookingButton;

    // Review Management
    private ListView<Review> reviewListView;
    private Button deleteReviewButton;
    private Tab adminTab;

    public AdminView(ObservableList<Room> rooms, ObservableList<Booking> bookings, ObservableList<Review> reviews) {
        initializeView(rooms, bookings, reviews);
    }

    private void initializeView(ObservableList<Room> rooms, ObservableList<Booking> bookings, ObservableList<Review> reviews) {
        VBox layout = new VBox(20);
        layout.setPadding(new Insets(20));
        layout.setStyle("-fx-background-color: linear-gradient(to bottom, #d3cce3, #e9e4f0);");

        Label title = new Label("Admin Panel");
        title.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        title.setTextFill(Color.web("#512da8"));

        adminTabPane = new TabPane();

        // Room Management Tab
        Tab roomManagementTab = createRoomManagementTab(rooms);
        adminTabPane.getTabs().add(roomManagementTab);

        // Booking Management Tab
        Tab bookingManagementTab = createBookingManagementTab(bookings);
        adminTabPane.getTabs().add(bookingManagementTab);

        // Review Management Tab
        Tab reviewManagementTab = createReviewManagementTab(reviews);
        adminTabPane.getTabs().add(reviewManagementTab);

        layout.getChildren().addAll(title, adminTabPane);
        this.view = layout;
        adminTab = new Tab("Admin", layout);
        adminTab.setClosable(false);
    }

    private Tab createRoomManagementTab(ObservableList<Room> rooms) {
        VBox roomLayout = new VBox(15);
        roomLayout.setPadding(new Insets(15));

        Label roomTitle = new Label("Room Management");
        roomTitle.setFont(Font.font("Arial", FontWeight.BOLD, 16));

        roomListView = new ListView<>(rooms);
        roomListView.setCellFactory(lv -> new ListCell<Room>() {
            @Override
            protected void updateItem(Room room, boolean empty) {
                super.updateItem(room, empty);
                setText(empty ? null : "Room #" + room.getRoomNumber() + " (" + room.getType() + ") - " + (room.isAvailable() ? "Available" : "Occupied"));
            }
        });

        HBox roomButtons = new HBox(10);
        addRoomButton = new Button("Add Room");
        updateRoomButton = new Button("Update Room");
        deleteRoomButton = new Button("Delete Room");
        roomButtons.getChildren().addAll(addRoomButton, updateRoomButton, deleteRoomButton);

        roomLayout.getChildren().addAll(roomTitle, roomListView, roomButtons);
        return new Tab("Rooms", roomLayout);
    }

    private Tab createBookingManagementTab(ObservableList<Booking> bookings) {
        VBox bookingLayout = new VBox(15);
        bookingLayout.setPadding(new Insets(15));

        Label bookingTitle = new Label("Booking Management");
        bookingTitle.setFont(Font.font("Arial", FontWeight.BOLD, 16));

        bookingListView = new ListView<>(bookings);
        bookingListView.setCellFactory(lv -> new ListCell<Booking>() {
            @Override
            protected void updateItem(Booking booking, boolean empty) {
                super.updateItem(booking, empty);
                if (empty || booking == null) {
                    setText(null);
                } else {
                    setText("Booking #" + booking.getBookingId() + ", Room #" + booking.getRoom().getRoomNumber() +
                            ", Customer: " + booking.getCustomer().getName() +
                            ", Check-in: " + booking.getCheckInDate() +
                            ", Check-out: " + booking.getCheckOutDate());
                }
            }
        });

        HBox bookingButtons = new HBox(10);
        updateBookingButton = new Button("Update Booking");
        deleteBookingButton = new Button("Delete Booking");
        bookingButtons.getChildren().addAll(updateBookingButton, deleteBookingButton);

        bookingLayout.getChildren().addAll(bookingTitle, bookingListView, bookingButtons);
        return new Tab("Bookings", bookingLayout);
    }

    private Tab createReviewManagementTab(ObservableList<Review> reviews) {
        VBox reviewLayout = new VBox(15);
        reviewLayout.setPadding(new Insets(15));

        Label reviewTitle = new Label("Review Management");
        reviewTitle.setFont(Font.font("Arial", FontWeight.BOLD, 16));

        reviewListView = new ListView<>(reviews);
        reviewListView.setCellFactory(lv -> new ListCell<Review>() {
            @Override
            protected void updateItem(Review review, boolean empty) {
                super.updateItem(review, empty);
                if (empty || review == null) {
                    setText(null);
                } else {
                    setText("Review #" + review.getReviewId() + " by " + review.getCustomer().getName() + ": " + review.getComment() + " (Rating: " + review.getRating() + ")");
                }
            }
        });

        deleteReviewButton = new Button("Delete Review");

        reviewLayout.getChildren().addAll(reviewTitle, reviewListView, deleteReviewButton);
        return new Tab("Reviews", reviewLayout);
    }

    public VBox getView() {
        return view;
    }

    public ListView<Room> getRoomListView() {
        return roomListView;
    }

    public Button getAddRoomButton() {
        return addRoomButton;
    }

    public Button getUpdateRoomButton() {
        return updateRoomButton;
    }

    public Button getDeleteRoomButton() {
        return deleteRoomButton;
    }

    public ListView<Booking> getBookingListView() {
        return bookingListView;
    }

    public Button getUpdateBookingButton() {
        return updateBookingButton;
    }

    public Button getDeleteBookingButton() {
        return deleteBookingButton;
    }

    public ListView<Review> getReviewListView() {
        return reviewListView;
    }

    public Button getDeleteReviewButton() {
        return deleteReviewButton;
    }
    public Tab getAdminTab() {
        return adminTab;
    }

}