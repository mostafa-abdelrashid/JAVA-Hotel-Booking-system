package com.mycompany.hotel.bookingsystem.views;



import com.mycompany.hotel.bookingsystem.models.Hotel;
import com.mycompany.hotel.bookingsystem.models.rooms.Room;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class RoomsView {

    private final Hotel hotel;
    private final ObservableList<Room> availableRooms;
    private FlowPane roomCards;
    private ComboBox<String> filterCombo;
    private Tab roomsTab;

    public RoomsView(Hotel hotel, ObservableList<Room> availableRooms) {
        this.hotel = hotel;
        this.availableRooms = availableRooms;
        this.roomCards = new FlowPane(20, 20);
        this.roomCards.setAlignment(Pos.CENTER);
        this.filterCombo = new ComboBox<>();
        initializeView();
        bindData();
    }

    private void initializeView() {
        VBox layout = new VBox(20);
        layout.setPadding(new Insets(20));
        layout.setStyle("-fx-background-color: linear-gradient(to bottom, #a1c4fd, #c2e9fb);");

        Label title = new Label("Available Rooms");
        title.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        title.setTextFill(Color.web("#3a4a6d"));

        // Filter controls
        HBox filterBox = new HBox(10);
        filterBox.setAlignment(Pos.CENTER_LEFT);

        Label filterLabel = new Label("Filter by Type:");
        filterCombo.getItems().addAll("All", "Single", "Double", "Suite");
        filterCombo.setValue("All");

        filterBox.getChildren().addAll(filterLabel, filterCombo);

        layout.getChildren().addAll(title, filterBox, roomCards);
        view = layout;
        roomsTab = new Tab("Rooms", layout);
        roomsTab.setClosable(false);
    }

    private void bindData() {
        // Bind to available rooms
        availableRooms.addListener((javafx.collections.ListChangeListener.Change<? extends Room> c) -> {
            roomCards.getChildren().clear();
            availableRooms.forEach(room -> {
                RoomCardView card = new RoomCardView(room);
                roomCards.getChildren().add(card.getView());
            });
        });

        // Initial population
        availableRooms.forEach(room -> {
            RoomCardView card = new RoomCardView(room);
            roomCards.getChildren().add(card.getView());
        });
    }

    public void setFilterAction(javafx.event.EventHandler<javafx.event.ActionEvent> handler) {
        filterCombo.setOnAction(handler);
    }

    public VBox getView() {
        return view;
    }

    public ComboBox<String> getFilterCombo() {
        return filterCombo;
    }
    public Tab getRoomsTab() {
        return roomsTab;
    }
    private VBox view;
}