
package com.mycompany.hotel.bookingsystem.models.notifications;

public abstract class Notification {
    private static  int  Notification_ID;
        private String message;
        
        abstract void  send();
        
        //getters
        public int get_Notification_ID(){
            return Notification_ID;
        }
        public String get_message(){
            return message;
        } 
      
        //setters
        public void set_Notification_ID(int Notification_ID){
            if (Notification_ID < 0) {
                throw new IllegalArgumentException("Notification ID cannot be negative.");
            }
            this.Notification_ID=Notification_ID;
        }
        public void set_message(String message){
            if (message == null || message.trim().isEmpty()) {
                throw new IllegalArgumentException("Message cannot be null or empty.");
            }
            this.message =message; 
        }
    }

