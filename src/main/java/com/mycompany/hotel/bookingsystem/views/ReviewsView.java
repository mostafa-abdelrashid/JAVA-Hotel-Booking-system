package com.mycompany.hotel.bookingsystem.views;

import com.mycompany.hotel.bookingsystem.models.users.Customer; // Assuming Customer model exists
import com.mycompany.hotel.bookingsystem.models.reviews.Review; // Assuming Review model existsimport javafx.collections.ObservableList;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class ReviewsView {

    private VBox view;
    private TextField nameField;
    private ComboBox<Integer> ratingCombo;
    private TextArea commentArea;
    private Button submitButton;
    private ListView<Review> reviewList;
    private Tab reviewsTab;

    public ReviewsView(ObservableList<Review> reviews) {
        initializeView(reviews);
    }

    private void initializeView(ObservableList<Review> reviews) {
        VBox layout = new VBox(20);
        layout.setPadding(new Insets(20));
        layout.setStyle("-fx-background-color: linear-gradient(to bottom, #84fab0, #8fd3f4);");

        Label title = new Label("Customer Reviews");
        title.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        title.setTextFill(Color.web("#1e8449"));

        // Review form
        VBox reviewForm = new VBox(10);
        reviewForm.setPadding(new Insets(15));
        reviewForm.setStyle("-fx-background-color: white; -fx-background-radius: 10;");

        Label formTitle = new Label("Write a Review");
        formTitle.setFont(Font.font("Arial", FontWeight.BOLD, 14));

        nameField = new TextField();
        nameField.setPromptText("Your Name");

        ratingCombo = new ComboBox<>();
        ratingCombo.getItems().addAll(1, 2, 3, 4, 5);
        ratingCombo.setPromptText("Rating (1-5)");

        commentArea = new TextArea();
        commentArea.setPromptText("Your review...");
        commentArea.setPrefRowCount(3);

        submitButton = new Button("Submit Review");
        submitButton.setStyle("-fx-background-color: #1e8449; -fx-text-fill: white;");

        reviewForm.getChildren().addAll(formTitle, nameField, ratingCombo, commentArea, submitButton);

        // Review list
        reviewList = new ListView<>(reviews);
        reviewList.setStyle("-fx-background-color: white; -fx-background-radius: 10;");
        reviewList.setCellFactory(param -> new ListCell<Review>() {
            @Override
            protected void updateItem(Review review, boolean empty) {
                super.updateItem(review, empty);
                if (empty || review == null) {
                    setText(null);
                    setGraphic(null);
                } else {
                    // Use the custom ReviewItemView
                    setGraphic(new ReviewItemView(review));
                }
            }
        });

        layout.getChildren().addAll(title, reviewForm, reviewList);
        this.view = layout;
        reviewsTab = new Tab("Reviews", layout);
        reviewsTab.setClosable(false);
    }

    public VBox getView() {
        return view;
    }

    public TextField getNameField() {
        return nameField;
    }

    public ComboBox<Integer> getRatingCombo() {
        return ratingCombo;
    }

    public TextArea getCommentArea() {
        return commentArea;
    }

    public Button getSubmitButton() {
        return submitButton;
    }
    public Tab getReviewsTab() {
        return reviewsTab;
    }
}