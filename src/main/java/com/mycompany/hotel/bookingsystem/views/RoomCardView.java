package com.mycompany.hotel.bookingsystem.views;


import com.mycompany.hotel.bookingsystem.models.rooms.Room;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class RoomCardView {

    private final Room room;
    private final VBox card;
    private Button bookButton;

    public RoomCardView(Room room) {
        this.room = room;
        this.card = createCard();
    }

    private VBox createCard() {
        VBox card = new VBox(10);
        card.setPadding(new Insets(15));
        card.setStyle("-fx-background-color: white; -fx-background-radius: 10; -fx-border-radius: 10; " +
                "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.1), 5, 5, 0, 0);");
        card.setPrefWidth(250);

        Label roomLabel = new Label("Room #" + room.getRoomNumber());
        roomLabel.setFont(Font.font("Arial", FontWeight.BOLD, 16));
        roomLabel.setTextFill(Color.web("#3a4a6d"));

        Label typeLabel = new Label("Type: " + room.getType());
        Label priceLabel = new Label("Price: $" + room.getPrice() + "/night");
        Label capacityLabel = new Label("Capacity: " + room.getCapacity() + " person(s)");
        Label statusLabel = new Label(room.isAvailable() ? "Available" : "Occupied");
        statusLabel.setTextFill(room.isAvailable() ? Color.GREEN : Color.RED);

        bookButton = new Button("Book Now");
        bookButton.setStyle("-fx-background-color: #4a47a3; -fx-text-fill: white;");
        bookButton.setDisable(!room.isAvailable());

        card.getChildren().addAll(roomLabel, typeLabel, priceLabel, capacityLabel, statusLabel, bookButton);
        return card;
    }

    public Button getBookButton() {
        return bookButton;
    }

    public VBox getView() {
        return card;
    }

    public Room getRoom() {
        return room;
    }
}