package com.com3014.appointmentservice.exceptions;

public class AppointmentIdnotFoundException extends RuntimeException {
    public AppointmentIdnotFoundException(String errorMessage) {
        super(errorMessage);
    }
}