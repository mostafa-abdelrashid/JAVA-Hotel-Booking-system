package com.mycompany.hotel.bookingsystem.models.reviews;

import com.mycompany.hotel.bookingsystem.exceptions.InvalidReviewException;
import com.mycompany.hotel.bookingsystem.models.users.Customer;

import java.util.Date;
import java.util.Objects;

public final class Review {
    private final int reviewId;       // Immutable
    private final Customer customer;  // Immutable
    private final int rating;        // Validated (1-5)
    private final String comment;    // Validated (non-empty)
    private final Date date;         // Immutable (defensive copy)

    public Review(int reviewId, Customer customer, int rating,
                  String comment, Date date) throws InvalidReviewException {
        this.reviewId = reviewId;
        this.customer = Objects.requireNonNull(customer, "Customer cannot be null");
        this.rating = validateRating(rating);
        this.comment = validateComment(comment);
        this.date = new Date(date.getTime());  // Defensive copy
    }

    // Validation helpers
    private static int validateRating(int rating) throws InvalidReviewException {
        if (rating < 1 || rating > 5) {
            throw new InvalidReviewException("Rating must be between 1 and 5");
        }
        return rating;
    }

    private static String validateComment(String comment) throws InvalidReviewException {
        if (comment == null || comment.trim().isEmpty()) {
            throw new InvalidReviewException("Comment cannot be empty");
        }
        return comment.trim();
    }

    // Getters (no setters; immutable)
    public int getReviewId() { return reviewId; }
    public Customer getCustomer() { return customer; }
    public int getRating() { return rating; }
    public String getComment() { return comment; }
    public Date getDate() { return new Date(date.getTime()); }  // Defensive copy

    // Display method
    public void displayReview() {
        System.out.printf("Review ID: %d/nCustomer: %s/nRating: %d/5/nComment: %s/nDate: %s",
                reviewId, customer.getName(), rating, comment, date
        );
    }
}