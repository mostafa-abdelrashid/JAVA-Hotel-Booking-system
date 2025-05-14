package com.mycompany.hotel.bookingsystem.controllers;

import com.mycompany.hotel.bookingsystem.App;
import com.mycompany.hotel.bookingsystem.exceptions.InvalidCodeException;
import com.mycompany.hotel.bookingsystem.models.Hotel;
import com.mycompany.hotel.bookingsystem.models.offers.Offer;
import com.mycompany.hotel.bookingsystem.models.offers.SeasonalOffer;
import com.mycompany.hotel.bookingsystem.models.offers.SpecialCodeOffer;
import com.mycompany.hotel.bookingsystem.views.OffersView;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

public class OffersController {

    private final OffersView offersView;
    private final Hotel hotel;
    private final ObservableList<Offer> offers;
    private final App mainApp;

    public OffersController(OffersView offersView, Hotel hotel, ObservableList<Offer> offers, App mainApp) {
        this.offersView = offersView;
        this.hotel = hotel;
        this.offers = offers;
        this.mainApp = mainApp;
        initializeController();
    }

    private void initializeController() {
        offersView.getAddSeasonalOfferButton().setOnAction(e -> handleAddSeasonalOffer());
        offersView.getAddSpecialCodeOfferButton().setOnAction(e -> handleAddSpecialCodeOffer());
    }

    private void handleAddSeasonalOffer() {
        LocalDate startDate = offersView.getSeasonalStartDatePicker().getValue();
        LocalDate endDate = offersView.getSeasonalEndDatePicker().getValue();
        String discountText = offersView.getSeasonalDiscountField().getText();

        if (startDate == null || endDate == null || discountText.isEmpty()) {
            mainApp.showAlert("Error", "Please fill in all fields for the seasonal offer.");
            return;
        }

        double discount;
        try {
            discount = Double.parseDouble(discountText);
        } catch (NumberFormatException e) {
            mainApp.showAlert("Error", "Invalid discount format. Please enter a number between 0 and 1.");
            return;
        }

        if (discount < 0 || discount > 1) {
            mainApp.showAlert("Error", "Discount rate must be between 0 and 1.");
            return;
        }

        Date startDateDate = Date.from(startDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
        Date endDateDate = Date.from(endDate.atStartOfDay(ZoneId.systemDefault()).toInstant());

        try {
            Offer seasonalOffer = new SeasonalOffer(discount, startDateDate, endDateDate);
            hotel.addOffer(seasonalOffer);
            offers.add(seasonalOffer);
            offersView.getSeasonalOfferListView().refresh();
            mainApp.showAlert("Success", "Seasonal offer added successfully.");
            clearSeasonalForm();

        } catch (Hotel.InvalidOfferException e) {
            mainApp.showAlert("Error", e.getMessage());
        } catch (Hotel.HotelOperationException e) {
            throw new RuntimeException(e);
        }
    }

    private void handleAddSpecialCodeOffer() {
        String promoCode = offersView.getSpecialCodeField().getText();
        String discountText = offersView.getSpecialCodeDiscountField().getText();

        if (promoCode.isEmpty() || discountText.isEmpty()) {
            mainApp.showAlert("Error", "Please fill in all fields for the special code offer.");
            return;
        }

        double discount;
        try {
            discount = Double.parseDouble(discountText);
        } catch (NumberFormatException e) {
            mainApp.showAlert("Error", "Invalid discount format. Please enter a number between 0 and 1.");
            return;
        }

        if (discount < 0 || discount > 1) {
            mainApp.showAlert("Error", "Discount rate must be between 0 and 1.");
            return;
        }

        try {
            SpecialCodeOffer.addCode(promoCode, discount); // Static method call
            Offer specialCodeOffer = new SpecialCodeOffer(promoCode);
            hotel.addOffer(specialCodeOffer);
            offers.add(specialCodeOffer);
            offersView.getSpecialCodeOfferListView().refresh();
            mainApp.showAlert("Success", "Special code offer added successfully.");
            clearSpecialCodeForm();
        } catch (IllegalArgumentException | IllegalStateException | InvalidCodeException e) {
            mainApp.showAlert("Error", e.getMessage());
        } catch (Hotel.InvalidOfferException | Hotel.HotelOperationException e) {
            mainApp.showAlert("Error", e.getMessage());
        }
    }

    private void clearSeasonalForm() {
        offersView.getSeasonalStartDatePicker().setValue(null);
        offersView.getSeasonalEndDatePicker().setValue(null);
        offersView.getSeasonalDiscountField().clear();
    }

    private void clearSpecialCodeForm() {
        offersView.getSpecialCodeField().clear();
        offersView.getSpecialCodeDiscountField().clear();
    }
}

