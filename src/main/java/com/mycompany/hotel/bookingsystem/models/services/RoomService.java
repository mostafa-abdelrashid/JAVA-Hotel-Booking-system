package com.mycompany.hotel.bookingsystem.models.services;

import com.mycompany.hotel.bookingsystem.exceptions.InvalidServiceException;


public class RoomService extends Service {
    private String mealType;

    public RoomService(int serviceId, String name, String description, double price, String mealType)
            throws InvalidServiceException {
        super(serviceId, name, description, price);
        if (mealType == null || mealType.isEmpty()) {
            throw new InvalidServiceException("Meal type must not be empty.");
        }
        this.mealType = mealType;
    }

    public void setMealType(String mealType) {
        this.mealType = mealType;
    }

    public String getMealType() {
        return mealType;
    }

    @Override
    public double useService() {
        System.out.println("Room Service: Serving " + mealType);
        return price;
    }
}