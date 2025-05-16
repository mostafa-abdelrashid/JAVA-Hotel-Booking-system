package com.mycompany.hotel.bookingsystem.controllers;

import com.mycompany.hotel.bookingsystem.exceptions.InvalidEmailException;
import com.mycompany.hotel.bookingsystem.exceptions.InvalidServiceException;
import com.mycompany.hotel.bookingsystem.models.bookings.Booking;
import com.mycompany.hotel.bookingsystem.models.offers.SpecialCodeOffer;
import com.mycompany.hotel.bookingsystem.models.payment.*;
import com.mycompany.hotel.bookingsystem.models.rooms.*;
import com.mycompany.hotel.bookingsystem.models.services.*;
import com.mycompany.hotel.bookingsystem.models.users.Customer;
import com.mycompany.hotel.bookingsystem.exceptions.InvalidServiceException;
import java.sql.Date;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class BookingController {
    private final Map<Integer, Booking> bookings = new HashMap<>();
    private final Map<Integer, Service> availableServices = new HashMap<>();
    private int bookingIdCounter = 1;

    // Add a list to store all rooms.  This is important for displaying *all* rooms.
    private final List<Room> allRooms = new ArrayList<>();

    public BookingController() throws InvalidServiceException {
        initializeServices();
        initializeRooms(); // Initialize rooms
    }

    private void initializeServices() throws InvalidServiceException {
        // Room Services
        availableServices.put(1, new RoomService(1, "Breakfast", "Continental breakfast", 15.0, "Continental"));
        availableServices.put(2, new RoomService(2, "Dinner", "Gourmet dinner service", 35.0, "Gourmet"));

        // Laundry Services
        availableServices.put(3, new LaundryService(3, "Express Laundry", "Next-day service", 25.0, 5));
        availableServices.put(4, new LaundryService(4, "Standard Laundry", "3-day service", 15.0, 10));

        // Spa Services
        availableServices.put(5, new SpaService(5, "Basic Package", "Sauna and massage", 50.0, "Basic"));
        availableServices.put(6, new SpaService(6, "Premium Package", "Full spa experience", 100.0, "Premium"));
    }

    private void initializeRooms() {
        allRooms.add(new SingleRoom(101, 100));
        allRooms.add(new SingleRoom(102, 100));
        allRooms.add(new SingleRoom(103, 100));
        allRooms.add(new DoubleRoom(201, 150));
        allRooms.add(new DoubleRoom(202, 150));
        allRooms.add(new DoubleRoom(203, 150));
        allRooms.add(new SuiteRoom(301, 250));
        allRooms.add(new SuiteRoom(302, 250));
        allRooms.add(new SuiteRoom(303, 250));
        allRooms.add(new SuiteRoom(304, 350));
    }

    public List<Room> getAvailableRooms() {
        return allRooms;
    }

    public String processBooking(String name, String email,
                                 Date checkIn, Date checkOut,
                                 String roomType,
                                 Map<Integer, String> selectedServicesWithValues,
                                 String offerCode,
                                 boolean isCreditCard,
                                 String cardNum, String expiry,
                                 String holder, String cvv,
                                 String paypalEmail) {
        Room roomToRemove = null; // Temporarily store the room to remove
        try {
            Customer customer = new Customer(name, email);

            // Create room
            Room room = createRoom(roomType);
            if (room == null) {
                return "⚠️ Error: No room of type " + roomType + " available.";
            }
            roomToRemove = room; // Store the room to remove on success

            // Create booking
            Booking booking = new Booking(bookingIdCounter++, customer, room, checkIn, checkOut);
            bookings.put(booking.getBookingId(), booking);

            // Add selected services
            double servicesTotal = addServicesToBooking(booking, selectedServicesWithValues); // Changed method call

            // Apply offer if present
            double totalPrice = booking.getTotalPrice() + servicesTotal;
            if (offerCode != null && !offerCode.trim().isEmpty()) {
                try {
                    SpecialCodeOffer offer = new SpecialCodeOffer(offerCode.trim());
                    totalPrice = offer.applyOffer(totalPrice);
                    booking.setOfferApplied(offer);
                } catch (IllegalArgumentException e) {
                    return "⚠️ Error: " + e.getMessage();
                }
            }

            // Process payment
            Payment payment = createPayment(isCreditCard, cardNum, expiry, holder, cvv, paypalEmail);
            if (!payment.pay(totalPrice)) {
                return "❌ Payment failed. Please try another payment method.";
            }

            // Remove the booked room from the list of all rooms (only on success).
            if (roomToRemove != null) {
                allRooms.remove(roomToRemove);
            }

            return completeBooking(booking, payment, totalPrice);

        } catch (InvalidEmailException e) {
            System.out.println("InvalidEmailException caught: " + e.getMessage());
            return "⚠️ Error: " + e.getMessage();
        } catch (Exception ex) {
            // If a room was selected but booking failed, we should add it back.
            if (roomToRemove != null) {
                // Determine the type of the room and add a new instance
                int roomNumber = roomToRemove.getRoomNumber();
                double price = roomToRemove.getPrice();

                if (roomToRemove instanceof SingleRoom) {
                    allRooms.add(new SingleRoom(roomNumber, price));
                } else if (roomToRemove instanceof DoubleRoom) {
                    allRooms.add(new DoubleRoom(roomNumber, price));
                } else if (roomToRemove instanceof SuiteRoom) {
                    allRooms.add(new SuiteRoom(roomNumber, price));
                }
            }
            return "⚠️ Error: " + ex.getMessage();
        }
    }

    private Room createRoom(String roomType) {
        List<Room> matchingRooms = new ArrayList<>();
        for (Room room : allRooms) {
            if (room.getClass().getSimpleName().equalsIgnoreCase(roomType.replace(" ", ""))) {
                matchingRooms.add(room);
            }
        }
        if (!matchingRooms.isEmpty()) {

            Room selectedRoom = matchingRooms.get(0);
            return selectedRoom;
        }
        return null; // Return null if no room is found
    }

    private double addServicesToBooking(Booking booking, Map<Integer, String> selectedServicesWithValues) { // Changed method signature
        double servicesTotal = 0;
        for (Map.Entry<Integer, String> entry : selectedServicesWithValues.entrySet()) {
            int serviceId = entry.getKey();
            String serviceValue = entry.getValue();
            Service service = availableServices.get(serviceId);
            if (service != null) {
                try {
                    booking.addService(service);
                    servicesTotal += processServiceValue(booking, service, serviceValue); // Process the value
                } catch (InvalidServiceException e) {
                    throw new RuntimeException(e);
                }
            } else {
                System.out.println("Service with ID " + serviceId + " not found.");
            }
        }
        return servicesTotal;
    }

    private double processServiceValue(Booking booking, Service service, String serviceValue) {
        double serviceCost = service.getPrice(); // Start with the base price
        if (service instanceof LaundryService) {
            if (serviceValue != null && !serviceValue.isEmpty()) {
                try {
                    int clothesCount = Integer.parseInt(serviceValue);
                    ((LaundryService) service).setClothesCount(clothesCount);
                    serviceCost += clothesCount * 1.5;
                } catch (NumberFormatException e) {
                    System.out.println("Invalid clothes count: " + serviceValue);
                    return 0;
                }
            }
        } else if (service instanceof RoomService) {

            if (serviceValue != null && !serviceValue.isEmpty()) {
                ((RoomService) service).setMealType(serviceValue);
            }
        } else if (service instanceof SpaService) {
            if (serviceValue != null && !serviceValue.isEmpty()) {
                ((SpaService) service).setSpaPackage(serviceValue);
            }
        }
        return serviceCost;
    }

    private Payment createPayment(boolean isCreditCard,
                                  String cardNum, String expiry,
                                  String holder, String cvv,
                                  String paypalEmail) throws Exception {
        if (isCreditCard) {
            if (expiry == null || expiry.trim().isEmpty()) {
                throw new IllegalArgumentException("Expiry date is required for credit card payments.");
            }
            YearMonth expiryDate = YearMonth.parse(expiry, DateTimeFormatter.ofPattern("MM/yy"));
            return new CreditCardPayment(cardNum, expiryDate, holder, cvv);
        } else {
            if (paypalEmail == null || paypalEmail.trim().isEmpty()) {
                throw new IllegalArgumentException("PayPal email is required for PayPal payments.");
            }
            return new PayPalPayment(paypalEmail);
        }
    }


    private String completeBooking(Booking booking, Payment payment, double amount) {
        return generateSuccessMessage(booking, amount);
    }

    private String generateSuccessMessage(Booking booking, double amount) {
        StringBuilder sb = new StringBuilder();
        sb.append("✅ Booking successful!\n");
        sb.append(String.format("Booking ID: %d\n", booking.getBookingId()));
        sb.append(String.format("Customer: %s\n", booking.getCustomer().getName()));
        sb.append(String.format("Room: %s\n", booking.getRoom().toString()));
        sb.append(String.format("Check-in: %s\n", booking.getCheckInDate()));
        sb.append(String.format("Check-out: %s\n", booking.getCheckOutDate()));

        if (!booking.getServices().isEmpty()) {
            sb.append("\nSelected Services:\n");
            booking.getServices().forEach(service ->
                    sb.append(String.format("- %s: $%.2f\n", service.getName(), service.getPrice())));
        }

        sb.append(String.format("\nTotal Price: $%.2f", amount));
        return sb.toString();
    }

    public List<Service> getAvailableServices() {
        return new ArrayList<>(availableServices.values());
    }

    public String getBookingDetails(int bookingId) {
        Booking booking = bookings.get(bookingId);
        if (booking == null) {
            return "Booking not found with ID: " + bookingId;
        }
        return generateBookingDetails(booking);
    }

    private String generateBookingDetails(Booking booking) {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("Booking ID: %d\n", booking.getBookingId()));
        sb.append(String.format("Customer: %s\n", booking.getCustomer().getName()));
        sb.append(String.format("Room Type: %s\n", booking.getRoom().getClass().getSimpleName()));
        sb.append(String.format("Check-in: %s\n", booking.getCheckInDate()));
        sb.append(String.format("Check-out: %s\n", booking.getCheckOutDate()));

        if (!booking.getServices().isEmpty()) {
            sb.append("\nServices Included:\n");
            booking.getServices().forEach(service ->
                    sb.append(String.format("- %s ($%.2f)\n", service.getName(), service.getPrice())));
        }

        sb.append(String.format("\nTotal Paid: $%.2f", booking.getTotalPrice()));
        return sb.toString();
    }

    private boolean isValidOfferCode(String offerCode) {
        System.out.println("Checking offer code: '" + offerCode + "'");
        boolean isValid = offerCode.equalsIgnoreCase("SAVE10") || offerCode.equalsIgnoreCase("SPECIAL20");
        System.out.println("Is valid? " + isValid);
        return isValid;
    }
}
