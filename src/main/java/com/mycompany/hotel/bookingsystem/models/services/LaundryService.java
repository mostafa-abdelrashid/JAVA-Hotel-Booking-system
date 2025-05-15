package com.mycompany.hotel.bookingsystem.models.services;

import com.mycompany.hotel.bookingsystem.exceptions.InvalidServiceException;

public class LaundryService extends Service {
    private int clothesCount;

    public LaundryService(int serviceId, String name, String description, double price, int clothesCount)
            throws InvalidServiceException {
        super(serviceId, name, description, price);
        if (clothesCount < 0) {
            throw new InvalidServiceException("Clothes count must be non-negative.");
        }
        this.clothesCount = clothesCount;
    }

    public void setClothesCount(int clothesCount) {
        this.clothesCount = clothesCount;
    }
    public int getClothesCount() {
        return clothesCount;
    }

    @Override
    public double useService() {
        System.out.println("Laundry Service: Washing " + clothesCount + " clothes");
        return price + (clothesCount * 1.5);
    }
}
