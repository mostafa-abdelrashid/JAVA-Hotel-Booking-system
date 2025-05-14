package com.mycompany.hotel.bookingsystem.views;

import com.mycompany.hotel.bookingsystem.models.offers.Offer;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class OffersView {

    private VBox view;
    private TabPane offersTabPane;

    // Seasonal Offer Tab
    private DatePicker seasonalStartDatePicker;
    private DatePicker seasonalEndDatePicker;
    private TextField seasonalDiscountField;
    private Button addSeasonalOfferButton;
    private ListView<Offer> seasonalOfferListView;

    // Special Code Offer Tab
    private TextField specialCodeField;
    private TextField specialCodeDiscountField;
    private Button addSpecialCodeOfferButton;
    private ListView<Offer> specialCodeOfferListView;
    private Tab offersTab;

    public OffersView(ObservableList<Offer> offers) {
        initializeView(offers);
    }

    private void initializeView(ObservableList<Offer> offers) {
        VBox layout = new VBox(20);
        layout.setPadding(new Insets(20));
        layout.setStyle("-fx-background-color: linear-gradient(to bottom, #ffe082, #ffb74d);"); // Orange gradient

        Label title = new Label("Offers");
        title.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        title.setTextFill(Color.web("#e65100"));

        offersTabPane = new TabPane();

        // Seasonal Offer Tab
        Tab seasonalOfferTab = createSeasonalOfferTab();
        offersTabPane.getTabs().add(seasonalOfferTab);

        // Special Code Offer Tab
        Tab specialCodeOfferTab = createSpecialCodeOfferTab();
        offersTabPane.getTabs().add(specialCodeOfferTab);

        // Add offers lists to display
        seasonalOfferListView = new ListView<>(offers);
        seasonalOfferListView.setPrefHeight(150);
        specialCodeOfferListView = new ListView<>(offers);
        specialCodeOfferListView.setPrefHeight(150);

        layout.getChildren().addAll(title, offersTabPane, seasonalOfferListView, specialCodeOfferListView);
        this.view = layout;
        offersTab = new Tab("Offers", layout);
        offersTab.setClosable(false);
    }

    private Tab createSeasonalOfferTab() {
        VBox seasonalLayout = new VBox(15);
        seasonalLayout.setPadding(new Insets(15));
        seasonalLayout.setStyle("-fx-background-color: white; -fx-background-radius: 10;");

        Label seasonalTitle = new Label("Seasonal Offers");
        seasonalTitle.setFont(Font.font("Arial", FontWeight.BOLD, 16));
        seasonalTitle.setTextFill(Color.web("#d84315"));

        GridPane seasonalForm = new GridPane();
        seasonalForm.setHgap(10);
        seasonalForm.setVgap(10);

        Label startDateLabel = new Label("Start Date:");
        seasonalStartDatePicker = new DatePicker();
        seasonalStartDatePicker.setPromptText("Select start date");

        Label endDateLabel = new Label("End Date:");
        seasonalEndDatePicker = new DatePicker();
        seasonalEndDatePicker.setPromptText("Select end date");

        Label discountLabel = new Label("Discount (0-1):");
        seasonalDiscountField = new TextField();
        seasonalDiscountField.setPromptText("Enter discount rate");

        addSeasonalOfferButton = new Button("Add Seasonal Offer");
        addSeasonalOfferButton.setStyle("-fx-background-color: #d84315; -fx-text-fill: white;");

        seasonalForm.add(startDateLabel, 0, 0);
        seasonalForm.add(seasonalStartDatePicker, 1, 0);
        seasonalForm.add(endDateLabel, 0, 1);
        seasonalForm.add(seasonalEndDatePicker, 1, 1);
        seasonalForm.add(discountLabel, 0, 2);
        seasonalForm.add(seasonalDiscountField, 1, 2);
        seasonalForm.add(addSeasonalOfferButton, 0, 3, 2, 1);

        seasonalLayout.getChildren().addAll(seasonalTitle, seasonalForm);
        return new Tab("Seasonal", seasonalLayout);
    }

    private Tab createSpecialCodeOfferTab() {
        VBox specialCodeLayout = new VBox(15);
        specialCodeLayout.setPadding(new Insets(15));
        specialCodeLayout.setStyle("-fx-background-color: white; -fx-background-radius: 10;");

        Label specialCodeTitle = new Label("Special Code Offers");
        specialCodeTitle.setFont(Font.font("Arial", FontWeight.BOLD, 16));
        specialCodeTitle.setTextFill(Color.web("#d84315"));

        GridPane specialCodeForm = new GridPane();
        specialCodeForm.setHgap(10);
        specialCodeForm.setVgap(10);

        Label codeLabel = new Label("Promo Code:");
        specialCodeField = new TextField();
        specialCodeField.setPromptText("Enter promo code");

        Label discountLabel = new Label("Discount (0-1):");
        specialCodeDiscountField = new TextField();
        specialCodeDiscountField.setPromptText("Enter discount rate");

        addSpecialCodeOfferButton = new Button("Add Special Code Offer");
        addSpecialCodeOfferButton.setStyle("-fx-background-color: #d84315; -fx-text-fill: white;");

        specialCodeForm.add(codeLabel, 0, 0);
        specialCodeForm.add(specialCodeField, 1, 0);
        specialCodeForm.add(discountLabel, 0, 1);
        specialCodeForm.add(specialCodeDiscountField, 1, 1);
        specialCodeForm.add(addSpecialCodeOfferButton, 0, 2, 2, 1);

        specialCodeLayout.getChildren().addAll(specialCodeTitle, specialCodeForm);
        return new Tab("Special Code", specialCodeLayout);
    }

    public VBox getView() {
        return view;
    }

    public DatePicker getSeasonalStartDatePicker() {
        return seasonalStartDatePicker;
    }

    public DatePicker getSeasonalEndDatePicker() {
        return seasonalEndDatePicker;
    }

    public TextField getSeasonalDiscountField() {
        return seasonalDiscountField;
    }

    public Button getAddSeasonalOfferButton() {
        return addSeasonalOfferButton;
    }

    public TextField getSpecialCodeField() {
        return specialCodeField;
    }

    public TextField getSpecialCodeDiscountField() {
        return specialCodeDiscountField;
    }

    public Button getAddSpecialCodeOfferButton() {
        return addSpecialCodeOfferButton;
    }

    public ListView<Offer> getSeasonalOfferListView() {
        return seasonalOfferListView;
    }

    public ListView<Offer> getSpecialCodeOfferListView() {
        return specialCodeOfferListView;
    }
    public Tab getOffersTab() {
        return offersTab;
    }
}
