package com.hackaboss.PruebaTecnica4.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class RoomBookingDTO {
    private LocalDate dateFrom;
    private LocalDate dateTo;
    private Integer nights;
    private String place;
    private String hotelCode;
    private Integer peopleQ;
    private String roomType;
    private Double totalPrice;
    private String roomCode;
}
