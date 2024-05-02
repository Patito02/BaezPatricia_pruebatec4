package com.hackaboss.PruebaTecnica4.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class FlightBookingException extends Exception{

    public FlightBookingException(String message) {
        super(message);
    }

}
