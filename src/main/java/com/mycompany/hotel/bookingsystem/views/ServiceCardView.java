package com.mycompany.hotel.bookingsystem.views;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class ServiceCardView {

    private final String name;
    private final String description;
    private final double price;
    private final String detailLabelText;
    private HBox view;
    private Button addButton;
    private TextField detailField;

    public ServiceCardView(String name, String description, double price, String detailLabel) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.detailLabelText = detailLabel;
        initializeView();
    }

    private void initializeView() {
        HBox card = new HBox(15);
        card.setPadding(new Insets(15));
        card.setStyle("-fx-background-color: white; -fx-background-radius: 10; -fx-border-radius: 10;");
        card.setAlignment(Pos.CENTER_LEFT);

        VBox infoBox = new VBox(5);
        Label nameLabel = new Label(name);
        nameLabel.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        nameLabel.setTextFill(Color.web("#d35400"));

        Label descLabel = new Label(description);
        descLabel.setWrapText(true);
        Label priceLabel = new Label("$" + price);

        infoBox.getChildren().addAll(nameLabel, descLabel, priceLabel);

        VBox detailBox = new VBox(5);
        detailBox.setPadding(new Insets(0, 0, 0, 20));
        detailBox.getChildren().add(new Label(detailLabelText));

        detailField = new TextField();
        detailField.setPromptText("Enter details...");
        detailBox.getChildren().add(detailField);

        addButton = new Button("Add to Booking");
        addButton.setStyle("-fx-background-color: #d35400; -fx-text-fill: white;");

        card.getChildren().addAll(infoBox, detailBox, addButton);
        this.view = card;
    }

    public HBox getView() {
        return view;
    }

    public Button getAddButton() {
        return addButton;
    }

    public String getServiceName() {
        return name;
    }

    public TextField getDetailField() {
        return detailField;
    }
}