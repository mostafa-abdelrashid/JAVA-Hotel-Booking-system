package com.mycompany.hotel.bookingsystem.views;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class ServicesView {

    private VBox view;
    private ServiceCardView roomServiceCard;
    private ServiceCardView laundryCard;
    private ServiceCardView spaCard;
    private Tab servicesTab;
    public ServicesView() {
        initializeView();
    }

    private void initializeView() {
        VBox layout = new VBox(20);
        layout.setPadding(new Insets(20));
        layout.setStyle("-fx-background-color: linear-gradient(to bottom, #f6d365, #fda085);");

        Label title = new Label("Our Services");
        title.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        title.setTextFill(Color.web("#d35400"));

        // Service cards
        VBox serviceCards = new VBox(15);

        roomServiceCard = new ServiceCardView("Room Service", "Enjoy meals in your room", 150.0, "Meal Type:");
        laundryCard = new ServiceCardView("Laundry Service", "Professional laundry service", 100.0, "Clothes Count:");
        spaCard = new ServiceCardView("Spa Service", "Relaxing spa treatments", 50.0, "Package:");

        serviceCards.getChildren().addAll(roomServiceCard.getView(), laundryCard.getView(), spaCard.getView());
        layout.getChildren().addAll(title, serviceCards);
        this.view = layout;
        servicesTab = new Tab("Services", layout);
        servicesTab.setClosable(false);
    }

    public VBox getView() {
        return view;
    }

    public ServiceCardView getRoomServiceCard() {
        return roomServiceCard;
    }

    public ServiceCardView getLaundryCard() {
        return laundryCard;
    }

    public ServiceCardView getSpaCard() {
        return spaCard;
    }
    public Tab getServicesTab() {
        return servicesTab;
    }
}