package com.hackaboss.PruebaTecnica4.service;

import com.hackaboss.PruebaTecnica4.dto.RoomBookingDTO;
import com.hackaboss.PruebaTecnica4.exception.RoomBookingException;
import com.hackaboss.PruebaTecnica4.model.Person;
import com.hackaboss.PruebaTecnica4.model.RoomBooking;

import java.util.List;

public interface IRoomBookingService {
    RoomBooking saveRoomBooking(RoomBooking roomBooking) throws RoomBookingException;
    List<RoomBookingDTO> getRoomBookings();
    RoomBookingDTO findRoomBookingById(Long id);
    RoomBooking editRoomBooking(Long id, RoomBooking roomBooking);
    RoomBooking deleteRoomBooking(Long id);

}
