package com.mycompany.hotel.bookingsystem.models.services;

import com.mycompany.hotel.bookingsystem.exceptions.InvalidServiceException;

public abstract class Service {
    protected final int serviceId;  // Made final
    protected final String name;   // Made final
    protected String description;
    protected double price;

    public Service(int serviceId, String name, String description, double price)
            throws InvalidServiceException {
        if (price < 0) throw new InvalidServiceException("Price cannot be negative");
        this.serviceId = serviceId;
        this.name = name;
        this.description = description;
        this.price = price;
    }
    public int getServiceId() {
        return serviceId;
    }
    public String getName() {
        return name;
    }
    public String getDescription() {
        return description;
    }
    public double getPrice() {
        return price;
    }
    public abstract double useService();

    public void displayDetails() {
        System.out.println("[" + name + "] " + description + " - $" + price);
    }
}