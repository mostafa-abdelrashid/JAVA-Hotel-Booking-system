package com.mycompany.hotel.bookingsystem.models;

import com.mycompany.hotel.bookingsystem.models.bookings.Booking;
import com.mycompany.hotel.bookingsystem.models.offers.Offer;
import com.mycompany.hotel.bookingsystem.models.reviews.Review;
import com.mycompany.hotel.bookingsystem.models.rooms.Room;

import java.util.ArrayList;
import java.util.List;

public class Hotel {
    private List<Room> rooms;
    private List<Offer> offers;
    private List<Review> reviews;
    private List<Booking> bookings;

    public Hotel() {
        this.rooms = new ArrayList<>();
        this.offers = new ArrayList<>();
        this.reviews = new ArrayList<>();
        this.bookings = new ArrayList<>();
    }

    // Room-related methods
    public List<Room> searchAvailableRooms() {
        List<Room> availableRooms = new ArrayList<>();
        for (Room room : rooms) {
            if (room.isAvailable()) {
                availableRooms.add(room);
            }
        }
        return availableRooms;
    }

    public void addRoom(Room room) {
        if (room == null) {
            throw new IllegalArgumentException("Room cannot be null");
        }
        rooms.add(room);
    }

    public void removeRoom(Room room) {
        if (room == null) {
            throw new IllegalArgumentException("Room cannot be null");
        }
        rooms.remove(room);
    }

    // Offer-related methods
    public void addOffer(Offer offer) {
        if (offer == null) {
            throw new IllegalArgumentException("Offer cannot be null");
        }
        offers.add(offer);
    }

    public void removeOffer(Offer offer) {
        if (offer == null) {
            throw new IllegalArgumentException("Offer cannot be null");
        }
        offers.remove(offer);
    }

    // Review-related methods
    public void addReview(Review review) {
        if (review == null) {
            throw new IllegalArgumentException("Review cannot be null");
        }
        reviews.add(review);
    }

    public void removeReview(Review review) {
        if (review == null) {
            throw new IllegalArgumentException("Review cannot be null");
        }
        reviews.remove(review);
    }

    // Booking-related methods
    public void addBooking(Booking booking) {
        if (booking == null) {
            throw new IllegalArgumentException("Booking cannot be null");
        }
        bookings.add(booking);
    }

    public void removeBooking(Booking booking) {
        if (booking == null) {
            throw new IllegalArgumentException("Booking cannot be null");
        }
        bookings.remove(booking);
    }

    // Getters
    public List<Room> getRooms() {
        return new ArrayList<>(rooms);
    }

    public List<Offer> getOffers() {
        return new ArrayList<>(offers);
    }

    public List<Review> getReviews() {
        return new ArrayList<>(reviews);
    }

    public List<Booking> getBookings() {
        return new ArrayList<>(bookings);
    }
}