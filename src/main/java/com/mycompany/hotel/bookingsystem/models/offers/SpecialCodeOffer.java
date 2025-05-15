package com.mycompany.hotel.bookingsystem.models.offers;
import java.util.HashMap;

public final class SpecialCodeOffer extends Offer {
    private final String promoCode;  // Immutable
    private static final HashMap<String, Double> availableCodes = new HashMap<>();

    public SpecialCodeOffer(String promoCode) throws IllegalArgumentException {
        super(validatePromoCode(promoCode));  // Sets discountRate via parent
        this.promoCode = promoCode;
        setActive(true);  // Always active once constructed
    }

    // Static validation helper
    private static double validatePromoCode(String code) throws IllegalArgumentException {
        if (code == null || code.trim().isEmpty()) {
            throw new IllegalArgumentException("Promo code cannot be empty");
        }
        Double rate = availableCodes.get(code);
        if (rate == null) throw new IllegalArgumentException("Invalid promo code");
        return rate;
    }

    // Static admin methods (optional; could be moved to a separate utility class)
    public static void addCode(String code, double discount) throws IllegalArgumentException {
        if (discount < 0 || discount > 1) throw new IllegalArgumentException("Invalid discount rate");
        if (availableCodes.containsKey(code)) throw new IllegalArgumentException("Code already exists");
        availableCodes.put(code, discount);
    }

    public static void removeCode(String code) throws IllegalArgumentException {
        if (!availableCodes.containsKey(code)) throw new IllegalArgumentException("Code not found");
        availableCodes.remove(code);
    }

    // Getters
    public String getPromoCode() {
        return promoCode;
    }

    @Override
    public boolean checkOffer() {
        return isActive();  // Always active if constructed successfully
    }
}