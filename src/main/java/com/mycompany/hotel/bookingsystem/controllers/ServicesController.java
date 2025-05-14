package com.mycompany.hotel.bookingsystem.controllers;

import com.mycompany.hotel.bookingsystem.App;
import com.mycompany.hotel.bookingsystem.views.ServicesView;
import javafx.scene.control.TabPane;

public class ServicesController {

    private final ServicesView servicesView;
    private final App mainApp;

    public ServicesController(ServicesView servicesView, App mainApp) {
        this.servicesView = servicesView;
        this.mainApp = mainApp;
        initializeController();
    }

    private void initializeController() {
        servicesView.getRoomServiceCard().getAddButton().setOnAction(e -> handleAddService(servicesView.getRoomServiceCard().getServiceName(), servicesView.getRoomServiceCard().getDetailField().getText()));
        servicesView.getLaundryCard().getAddButton().setOnAction(e -> handleAddService(servicesView.getLaundryCard().getServiceName(), servicesView.getLaundryCard().getDetailField().getText()));
        servicesView.getSpaCard().getAddButton().setOnAction(e -> handleAddService(servicesView.getSpaCard().getServiceName(), servicesView.getSpaCard().getDetailField().getText()));
    }

    private void handleAddService(String serviceName, String details) {
        TabPane tabPane = (TabPane) servicesView.getView().getScene().getRoot();
        tabPane.getSelectionModel().select(0); // Switch to booking tab
        mainApp.showAlert("Service Added", serviceName + " service with details: '" + details + "' will be added to your booking");
        // In a real application, you would now update the booking model.
    }
}