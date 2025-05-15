package com.mycompany.hotel.bookingsystem.models.services;

import com.mycompany.hotel.bookingsystem.exceptions.InvalidServiceException;

public class SpaService extends Service {
    private String spaPackage;

    public SpaService(int serviceId, String name, String description, double price, String spaPackage)
            throws InvalidServiceException {
        super(serviceId, name, description, price);
        if (spaPackage == null || spaPackage.isEmpty()) {
            throw new InvalidServiceException("Spa package must not be empty.");
        }
        this.spaPackage = spaPackage;
    }

    public void setSpaPackage(String spaPackage) {
        this.spaPackage = spaPackage;
    }
    public String getSpaPackage() {
        return spaPackage;
    }

    @Override
    public double useService() {
        System.out.println("Spa Service: Providing " + spaPackage + " package");
        return price;
    }
}