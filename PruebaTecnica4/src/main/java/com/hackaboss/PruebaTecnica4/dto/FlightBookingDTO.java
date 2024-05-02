package com.hackaboss.PruebaTecnica4.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FlightBookingDTO {
    private String flightCode;
    private LocalDate date;
    private String origin;
    private String destination;
    private String seatType;
    private double totalPrice;
    private int peopleQ;

}
