package com.hackaboss.PruebaTecnica4.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class FlightDTO {
    private String flightCode;
    private LocalDate date;
    private String origin;
    private String destination;
    private Integer seatsQ;
    private Integer seatsAvailable;
    private String seatType;
    private Double flightPrice;
    private Boolean isComplete;

}
