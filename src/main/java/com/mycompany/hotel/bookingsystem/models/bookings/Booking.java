package com.mycompany.hotel.bookingsystem.models.bookings;

import com.mycompany.hotel.bookingsystem.exceptions.BookingOperationException;
import com.mycompany.hotel.bookingsystem.exceptions.InvalidBookingException;
import com.mycompany.hotel.bookingsystem.exceptions.InvalidOfferException;
import com.mycompany.hotel.bookingsystem.exceptions.InvalidServiceException;
import com.mycompany.hotel.bookingsystem.models.offers.Offer;
import com.mycompany.hotel.bookingsystem.models.rooms.Room;
import com.mycompany.hotel.bookingsystem.models.services.Service;
import com.mycompany.hotel.bookingsystem.models.users.Customer;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Booking {
    private int bookingId;
    private Customer customer;
    private Room room;
    private List<Service> services;
    private Date checkInDate;
    private Date checkOutDate;
    private double totalPrice;
    private BookingStatus status;
    private Offer offerApplied;

    public Booking(int bookingId, Customer customer, Room room,
                   Date checkInDate, Date checkOutDate) throws InvalidBookingException {
        if (customer == null) {
            throw new InvalidBookingException("Customer cannot be null");
        }
        if (room == null) {
            throw new InvalidBookingException("Room cannot be null");
        }
        if (checkInDate == null || checkOutDate == null) {
            throw new InvalidBookingException("Dates cannot be null");
        }
        if (checkInDate.after(checkOutDate)) {
            throw new InvalidBookingException("Check-in date must be before check-out date");
        }

        this.bookingId = bookingId;
        this.customer = customer;
        this.room = room;
        this.checkInDate = new Date(checkInDate.getTime());
        this.checkOutDate = new Date(checkOutDate.getTime());
        this.services = new ArrayList<>();
        this.status = BookingStatus.CONFIRMED;
        this.totalPrice = calculatePrice();
    }

    public double calculatePrice() {
        try {
            double basePrice = room.getPrice() * getNumberOfNights();
            double servicesPrice = services.stream().mapToDouble(Service::useService).sum();

            double finalPrice = basePrice + servicesPrice;

            if (offerApplied != null) {
                finalPrice = offerApplied.applyOffer(finalPrice);
            }

            this.totalPrice = finalPrice;
            return finalPrice;
        } catch (Exception e) {
            throw new BookingOperationException("Error calculating booking price", e);
        }
    }

    public void addService(Service service) throws InvalidServiceException {
        if (service == null) {
            throw new InvalidServiceException("Service cannot be null");
        }
        if (status != BookingStatus.CONFIRMED) {
            throw new InvalidServiceException("Cannot add services to a cancelled or completed booking");
        }

        try {
            services.add(service);
            calculatePrice(); // Recalculate total price
        } catch (Exception e) {
            throw new BookingOperationException("Error adding service to booking", e);
        }
    }

    public void cancel() throws BookingOperationException {
        if (status == BookingStatus.CANCELLED) {
            throw new BookingOperationException("Booking is already cancelled");
        }
        if (new Date().after(checkInDate)) {
            throw new BookingOperationException("Cannot cancel booking after check-in date");
        }

        try {
            this.status = BookingStatus.CANCELLED;
            // Additional cancellation logic could go here
        } catch (Exception e) {
            throw new BookingOperationException("Error cancelling booking", e);
        }
    }

    // Helper method
    private long getNumberOfNights() {
        long diff = checkOutDate.getTime() - checkInDate.getTime();
        return diff / (1000 * 60 * 60 * 24); // Convert milliseconds to days
    }







    // Getters and Setters
    public int getBookingId() {
        return bookingId;
    }

    public Customer getCustomer() {
        return customer;
    }

    public Room getRoom() {
        return room;
    }

    public List<Service> getServices() {
        return new ArrayList<>(services); // Defensive copy
    }

    public Date getCheckInDate() {
        return new Date(checkInDate.getTime()); // Defensive copy
    }

    public Date getCheckOutDate() {
        return new Date(checkOutDate.getTime()); // Defensive copy
    }

    public double getTotalPrice() {
        return totalPrice;
    }
    @Override
    public String toString() {
        return "Booking ID: " + this.getBookingId()+" Booking Price: " + this.getTotalPrice();
    }
    public BookingStatus getStatus() {
        return status;
    }

    public Offer getOfferApplied() {
        return offerApplied;
    }

    public void setOfferApplied(Offer offer) throws InvalidOfferException {
        if (offer == null) {
            throw new InvalidOfferException("Offer cannot be null");
        }
        if (status != BookingStatus.CONFIRMED) {
            throw new InvalidOfferException("Cannot apply offer to a cancelled or completed booking");
        }
        this.offerApplied = offer;
        calculatePrice(); // Recalculate with new offer
    }
}

// Supporting enums and interfaces
enum BookingStatus {
    CONFIRMED, CANCELLED, COMPLETED
}