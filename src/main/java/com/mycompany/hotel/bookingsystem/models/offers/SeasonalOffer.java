package com.mycompany.hotel.bookingsystem.models.offers;
import java.util.Date;

public final class SeasonalOffer extends Offer {
    private final Date startDate;  // Immutable
    private final Date endDate;    // Immutable

    public SeasonalOffer(double discountRate, Date startDate, Date endDate) throws IllegalArgumentException {
        super(discountRate);  // Parent validates discountRate
        if (startDate == null || endDate == null || startDate.after(endDate)) {
            throw new IllegalArgumentException("Invalid date range");
        }
        this.startDate = new Date(startDate.getTime());  // Defensive copy
        this.endDate = new Date(endDate.getTime());
        setActive(checkOffer());  // Updates parent's isActive
    }

    // Getters (no setters; immutable)
    public Date getStartDate() {
        return new Date(startDate.getTime());  // Defensive copy
    }

    public Date getEndDate() {
        return new Date(endDate.getTime());
    }

    @Override
    public boolean checkOffer() {
        Date now = new Date();
        return !now.before(startDate) && !now.after(endDate);
    }
}