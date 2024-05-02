package com.hackaboss.PruebaTecnica4.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class RoomDTO {

    private String roomCode;
    private String roomType;
    private double roomPrice;
    private String hotelCode;
    private String hotelName;
    private String place;

}
