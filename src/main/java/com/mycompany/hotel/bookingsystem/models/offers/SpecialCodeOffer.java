package com.mycompany.hotel.bookingsystem.models.offers;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public final class SpecialCodeOffer extends Offer {
    private final String promoCode;
    private static final HashMap<String, Double> availableCodes = new HashMap<>();

    // Static initializer block to load default codes
    static {
        availableCodes.put("SAVE10", 0.10);  // 10% discount
        availableCodes.put("SPECIAL20", 0.20);  // 20% discount
    }

    public SpecialCodeOffer(String promoCode) throws IllegalArgumentException {
        super(validatePromoCode(promoCode));
        this.promoCode = promoCode;
        setActive(true);
    }

    private static double validatePromoCode(String code) throws IllegalArgumentException {
        if (code == null || code.trim().isEmpty()) {
            throw new IllegalArgumentException("Promo code cannot be empty");
        }

        // Case-insensitive matching
        String normalizedCode = code.trim().toUpperCase();
        Double rate = availableCodes.get(normalizedCode);

        if (rate == null) {
            throw new IllegalArgumentException("Invalid promo code. Valid codes are: " + availableCodes.keySet());
        }
        return rate;
    }

    // Static admin methods
    public static void addCode(String code, double discountPercent) throws IllegalArgumentException {
        if (discountPercent < 0 || discountPercent > 100) {
            throw new IllegalArgumentException("Discount must be between 0-100");
        }
        String normalizedCode = code.trim().toUpperCase();
        if (availableCodes.containsKey(normalizedCode)) {
            throw new IllegalArgumentException("Code already exists");
        }
        availableCodes.put(normalizedCode, discountPercent/100.0);
    }

    public static void removeCode(String code) throws IllegalArgumentException {
        String normalizedCode = code.trim().toUpperCase();
        if (!availableCodes.containsKey(normalizedCode)) {
            throw new IllegalArgumentException("Code not found");
        }
        availableCodes.remove(normalizedCode);
    }

    // Getters
    public String getPromoCode() {
        return promoCode;
    }

    @Override
    public boolean checkOffer() {
        return isActive();
    }

    // Helper to get all valid codes (for UI display)
    public static Set<String> getValidCodes() {
        return new HashSet<>(availableCodes.keySet());
    }
}