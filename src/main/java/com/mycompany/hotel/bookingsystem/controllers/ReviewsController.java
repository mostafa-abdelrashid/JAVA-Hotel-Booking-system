package com.mycompany.hotel.bookingsystem.controllers;

import com.mycompany.hotel.bookingsystem.App; // Assuming App handles alerts
import com.mycompany.hotel.bookingsystem.models.users.Customer; // Assuming Customer model exists
import com.mycompany.hotel.bookingsystem.models.reviews.Review; // Assuming Review model exists
import com.mycompany.hotel.bookingsystem.models.Hotel; // Assuming Hotel model exists
import com.mycompany.hotel.bookingsystem.views.ReviewsView;
import javafx.collections.ObservableList;

import java.util.Date;

public class ReviewsController {

    private final ReviewsView reviewsView;
    private final Hotel hotel;
    private final ObservableList<Review> reviews;
    private final App mainApp; // Reference to the main application for alerts

    public ReviewsController(ReviewsView reviewsView, Hotel hotel, ObservableList<Review> reviews, App mainApp) {
        this.reviewsView = reviewsView;
        this.hotel = hotel;
        this.reviews = reviews;
        this.mainApp = mainApp;
        initializeController();
    }

    private void initializeController() {
        reviewsView.getSubmitButton().setOnAction(e -> handleSubmitReview());
    }

    private void handleSubmitReview() {
        try {
            String name = reviewsView.getNameField().getText();
            Integer rating = reviewsView.getRatingCombo().getValue();
            String comment = reviewsView.getCommentArea().getText();

            if (name.isEmpty()) {
                throw new IllegalArgumentException("Please enter your name");
            }
            if (rating == null) {
                throw new IllegalArgumentException("Please select a rating");
            }
            if (comment.isEmpty()) {
                throw new IllegalArgumentException("Please write your review");
            }

            // Create a dummy customer for the review (you might have a login system later)
            // Using a simple Customer constructor assuming it takes name, email, password
            Customer customer = new Customer(name, "guest@example.com", "password123");

            Review review = new Review(
                    reviews.size() + 1, // Simple ID assignment, might need a better strategy
                    customer,
                    rating,
                    comment,
                    new Date() // Current date
            );

            hotel.addReview(review); // Add to the main hotel model
            reviews.add(review);    // Add to the observable list for UI update

            mainApp.showAlert("Thank You", "Your review has been submitted!");

            // Clear form
            reviewsView.getNameField().clear();
            reviewsView.getRatingCombo().getSelectionModel().clearSelection();
            reviewsView.getCommentArea().clear();

        } catch (IllegalArgumentException ex) {
            mainApp.showAlert("Validation Error", ex.getMessage());
        } catch (Exception ex) {
            mainApp.showAlert("Error", "An unexpected error occurred: " + ex.getMessage());
        }
    }
}