package com.mycompany.hotel.bookingsystem.models.offers;

import com.mycompany.hotel.bookingsystem.exceptions.InvalidCodeException;

import java.util.HashMap;

public class SpecialCodeOffer extends Offer {
    private String promoCode;
    private static HashMap<String, Double> availableCodes = new HashMap<>();

    public SpecialCodeOffer(String promoCode) throws IllegalArgumentException, InvalidCodeException {
        if (promoCode == null || promoCode.trim().isEmpty()) throw new IllegalArgumentException("There is no code");
        if (availableCodes.get(promoCode) == null) throw new InvalidCodeException();
        this.promoCode = promoCode;
        this.discountRate = availableCodes.get(promoCode);
        this.isActive = true;
    }

    public String getPromoCode() {
        return promoCode;
    }

    @Override
    public boolean checkOffer() {
        return isActive;
    }

    static public void addCode(String code, double discount) throws IllegalArgumentException, IllegalStateException {
        if (discount < 0 || discount > 1) throw new IllegalArgumentException("Discount rate should be between 0 and 1");
        if (code == null || code.trim().isEmpty()) throw new IllegalArgumentException("There is no code");
        if (availableCodes.get(code) != null) throw new IllegalStateException("Code already exist");
        availableCodes.put(code, discount);
    }

    static public void removeCode(String code) throws IllegalArgumentException {
        if (code == null || code.trim().isEmpty()) throw new IllegalArgumentException("There is no code");
        if (availableCodes.get(code) == null) throw new IllegalStateException("Code already doesn't exist");
        availableCodes.remove(code);
    }
    /*
    The two methods above are for the admin to insert and remove promocodes
    */
}
