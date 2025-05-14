package com.mycompany.hotel.bookingsystem.models.offers;

import java.util.Date;

public class SeasonalOffer extends Offer {
    private Date startDate;
    private Date endDate;

    public SeasonalOffer(double discountRate, Date startDate, Date endDate) throws IllegalArgumentException {
        if (discountRate < 0 || discountRate > 1)
            throw new IllegalArgumentException("Discount rate should be between 0 and 1");
        this.discountRate = discountRate;
        this.startDate = startDate;
        this.endDate = endDate;
        this.isActive = this.checkOffer();
    }

    public Date getStartDate() {
        return startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    @Override
    public boolean checkOffer() {
        Date currentDate = new Date();
        if (currentDate.compareTo(startDate) < 0 || currentDate.compareTo(endDate) > 0) return false;
        else return true;
    }

}
