package com.mycompany.hotel.bookingsystem.models.offers;

public abstract class Offer {
    private final double discountRate;  // Immutable
    private boolean isActive;

    protected Offer(double discountRate) throws IllegalArgumentException {
        if (discountRate < 0 || discountRate > 1) {
            throw new IllegalArgumentException("Discount rate must be between 0 and 1");
        }
        this.discountRate = discountRate;
        this.isActive = false;  // Default inactive until validated by subclass
    }


    protected void setActive(boolean isActive) {
        this.isActive = isActive;
    }

    // Public getters (no setters for discountRate; it's immutable)
    public final double getDiscountRate() {
        return discountRate;
    }

    public final boolean isActive() {
        return isActive;
    }

    // Core method to apply discount (validates state)
    public final double applyOffer(double price) throws IllegalStateException, IllegalArgumentException {
        if (!isActive) throw new IllegalStateException("Offer is inactive");
        if (price < 0) throw new IllegalArgumentException("Price must be positive");
        return price * (1 - discountRate);
    }

    // Subclasses must implement validation logic
    public abstract boolean checkOffer();
}