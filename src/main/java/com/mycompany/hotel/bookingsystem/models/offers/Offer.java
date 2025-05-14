
package com.mycompany.hotel.bookingsystem.models.offers;


public abstract class Offer {
     protected boolean isActive;
    protected double discountRate;
    public double getDiscountRate(){
        return discountRate;
    }
    public double applyOffer(double price)throws IllegalStateException,IllegalArgumentException{
        if(!isActive) throw new IllegalStateException("The Offer cannot be applied");
        if(price < 0) throw new IllegalArgumentException("the price must be a positive value");
        return price-(price*discountRate);
    }
    public abstract boolean checkOffer();
}

