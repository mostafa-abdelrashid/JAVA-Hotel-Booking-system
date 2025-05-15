module com.mycompany.hotel.bookingsystem {
    requires javafx.controls;
    requires java.sql;
    exports com.mycompany.hotel.bookingsystem;
    exports com.mycompany.hotel.bookingsystem.models.users;
    exports com.mycompany.hotel.bookingsystem.models.bookings;
    exports com.mycompany.hotel.bookingsystem.models.offers;
    exports com.mycompany.hotel.bookingsystem.models.payment;
    exports com.mycompany.hotel.bookingsystem.models.reviews;
    exports com.mycompany.hotel.bookingsystem.models.rooms;
    exports com.mycompany.hotel.bookingsystem.models.services;
    exports com.mycompany.hotel.bookingsystem.models;
}
