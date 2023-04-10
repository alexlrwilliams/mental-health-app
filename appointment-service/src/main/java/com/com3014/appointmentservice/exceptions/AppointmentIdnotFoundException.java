package com.com3014.appointmentservice.exceptions;

public class AppointmentIdNotFoundException extends RuntimeException {
    public AppointmentIdNotFoundException(String errorMessage) {
        super(errorMessage);
    }
}