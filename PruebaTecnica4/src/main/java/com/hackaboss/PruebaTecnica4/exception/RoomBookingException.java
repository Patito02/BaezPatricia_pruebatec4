package com.hackaboss.PruebaTecnica4.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class RoomBookingException extends Exception{
    public RoomBookingException(String message) {
        super(message);
    }
}
