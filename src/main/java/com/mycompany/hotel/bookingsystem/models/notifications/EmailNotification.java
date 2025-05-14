package com.mycompany.hotel.bookingsystem.models.notifications;

public class EmailNotification extends Notification {
    //Constructor
    public EmailNotification() {
        try {
            set_Notification_ID(get_Notification_ID() + 1);
            set_message("Thank you for booking whith us. " + '\n' + "Your booking details are:" + '\n');
            //get Email from Customer
        } catch (IllegalArgumentException e) {
            System.out.println("Error in EmailNotification constructor: " + e.getMessage());
        }
    }

    @Override
    public void send() {
        // will use graphical inetrfernce in the future
        System.out.println(get_message());
    }
}
