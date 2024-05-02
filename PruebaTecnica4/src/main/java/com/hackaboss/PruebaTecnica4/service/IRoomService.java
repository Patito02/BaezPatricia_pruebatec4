package com.hackaboss.PruebaTecnica4.service;

import com.hackaboss.PruebaTecnica4.dto.RoomDTO;
import com.hackaboss.PruebaTecnica4.exception.RoomBookingException;
import com.hackaboss.PruebaTecnica4.model.Room;

import java.time.LocalDate;
import java.util.List;

public interface IRoomService {
    Room saveRoom(Room room);
    List<RoomDTO> getAllRooms();
    RoomDTO findRoomById(Long id);
    Room editRoom(Long id, Room room);
    Room deleteRoom(Long id) throws RoomBookingException;
    List<Room> getRoomsByType(String roomType);
    List<RoomDTO> getRoomByDestinationAndDate(LocalDate dateFrom, LocalDate dateTo,
                                              String destination);

}
