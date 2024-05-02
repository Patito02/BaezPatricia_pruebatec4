package com.hackaboss.PruebaTecnica4.service;

import com.hackaboss.PruebaTecnica4.dto.HotelDTO;
import com.hackaboss.PruebaTecnica4.exception.RoomBookingException;
import com.hackaboss.PruebaTecnica4.model.Hotel;

import java.util.List;

public interface IHotelService {
    Hotel saveHotel(Hotel hotel);
    List<HotelDTO> getAllHotels();
    HotelDTO findHotelById(String hotelCode);
    Hotel editHotel(String hotelCode, Hotel hotel);
    Hotel deleteHotel(String hotelCode) throws RoomBookingException;
}
