package com.mycompany.hotel.bookingsystem.views;
import com.mycompany.hotel.bookingsystem.models.users.Customer; // Assuming Customer model exists
import com.mycompany.hotel.bookingsystem.models.reviews.Review; // Assuming Review model exists
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

import java.text.SimpleDateFormat;

public class ReviewItemView extends VBox { // Extend VBox for simplicity

    private final Review review;
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    public ReviewItemView(Review review) {
        this.review = review;
        initializeView();
    }

    private void initializeView() {
        this.setSpacing(5);
        this.setPadding(new Insets(10));
        this.setStyle("-fx-border-color: #d5f5e3; -fx-border-width: 1; -fx-border-radius: 5;");

        HBox header = new HBox(10);
        header.setAlignment(Pos.CENTER_LEFT);

        Label nameLabel = new Label(review.getCustomer().getName());
        nameLabel.setFont(Font.font("Arial", FontWeight.BOLD, 12));

        Label ratingLabel = new Label("Rating: " + review.getRating() + "/5");
        ratingLabel.setTextFill(Color.web("#1e8449"));

        Label dateLabel = new Label(dateFormat.format(review.getDate()));
        dateLabel.setStyle("-fx-text-fill: #666;");

        header.getChildren().addAll(nameLabel, ratingLabel, dateLabel);

        Label commentLabel = new Label(review.getComment());
        commentLabel.setWrapText(true);

        this.getChildren().addAll(header, commentLabel);
    }
}