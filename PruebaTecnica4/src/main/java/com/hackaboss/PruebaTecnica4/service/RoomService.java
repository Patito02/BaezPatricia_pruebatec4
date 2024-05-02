package com.hackaboss.PruebaTecnica4.service;

import com.hackaboss.PruebaTecnica4.dto.RoomDTO;
import com.hackaboss.PruebaTecnica4.exception.RoomBookingException;
import com.hackaboss.PruebaTecnica4.model.Room;
import com.hackaboss.PruebaTecnica4.model.RoomBooking;
import com.hackaboss.PruebaTecnica4.repository.IRoomBookingRepository;
import com.hackaboss.PruebaTecnica4.repository.IRoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class RoomService implements IRoomService{

    @Autowired
    private IRoomRepository roomRepo;

    @Autowired
    private IRoomBookingRepository roomBookingRepo;

    @Override
    public Room saveRoom(Room room) {
        List<Room> listRooms = roomRepo.getAllRooms();
        Room addRoom = listRooms.stream()
                .filter(r->r.getHotel().getHotelCode().equals(room.getHotel().getHotelCode()))
                .filter(r->r.getRoomCode().equals(room.getRoomCode()))
                .findFirst().orElse(null);
        if(addRoom != null){
            return null;
        }
        room.setIsBooked(false);
        room.setStatus(true);
        return roomRepo.save(room);
    }

    @Override
    public List<RoomDTO> getAllRooms() {
        List<Room> listRooms = roomRepo.getAllRooms();
        return listRooms.stream()
                .map(this::toRoomDTO)
                .toList();

    }
    private RoomDTO toRoomDTO(Room room) {
        RoomDTO roomDTO = new RoomDTO();
        roomDTO.setRoomCode(room.getRoomCode());
        roomDTO.setRoomType(room.getRoomType());
        roomDTO.setRoomPrice(room.getRoomPrice());
        roomDTO.setHotelCode(room.getHotel().getHotelCode());
        roomDTO.setHotelName(room.getHotel().getName());
        roomDTO.setPlace(room.getHotel().getPlace());
        return roomDTO;
    }
    @Override
    public RoomDTO findRoomById(Long id) {
        Room findRoom = roomRepo.findRoomById(id);
        if(findRoom!=null){
            return toRoomDTO(findRoom);
        }
        return null;
    }

    @Override
    public Room editRoom(Long id, Room room) {
        Room editRoom = roomRepo.findRoomById(id);

        if (editRoom != null) {
            editRoom.setRoomCode(room.getRoomCode());
            editRoom.setRoomType(room.getRoomType());
            editRoom.setRoomPrice(room.getRoomPrice());
            editRoom.setHotel(room.getHotel());
            editRoom.setIsBooked(room.getIsBooked());
            return roomRepo.save(editRoom);
        }
        return null;
    }

    @Override
    public Room deleteRoom(Long id) throws RoomBookingException {
        Room deleteRoom = roomRepo.findRoomById(id);
        if (deleteRoom != null) {
            //verifico que no exista una roomBooking asociada al id de la habitacion
            List<RoomBooking> listRB= roomBookingRepo.getRoomBookings();
            if (!listRB.isEmpty()) {
                boolean hasRoomBooking = listRB.stream()
                        .anyMatch(fb -> fb.getRoom().getId().equals(id));
                if (hasRoomBooking) {
                    throw new RoomBookingException("Room booking linked to this room code. Please delete the room booking first.");
                }
            }
            deleteRoom.setStatus(false);
            return roomRepo.save(deleteRoom);
        }
        return null;
    }

    @Override
    public List<Room> getRoomsByType(String roomType) {
        List<Room> listRooms = roomRepo.getAllRooms();
        return listRooms.stream()
                .filter(r->r.getRoomType().equals(roomType))
                .toList();
    }

    @Override
    public List<RoomDTO> getRoomByDestinationAndDate(LocalDate dateFrom, LocalDate dateTo,
                                                  String destination) {

        List<Room> listRoomsAvailable = new ArrayList<>();
        //lista de habitaciones por destino
        List<Room> listRoom = roomRepo.getAllRooms().stream()
                .filter(r->r.getHotel().getPlace().equals(destination))
                .toList();
        //lista de roomBookings en las fechas solicitadas
        List<RoomBooking> listRBDatesNoAvailable = roomBookingRepo.findAll().stream()
                .filter(rb -> ( dateFrom.isBefore(rb.getDateTo()) && dateTo.isAfter(rb.getDateFrom()) ))
                .toList();

        for(Room r: listRoom){
            boolean existBooked = listRBDatesNoAvailable.stream()
                    .anyMatch(rb -> rb.getRoom().getId().equals(r.getId()));
            if(!existBooked){
                listRoomsAvailable.add(r);
            }
        }
        return listRoomsAvailable.stream()
                .map(this::toRoomDTO)
                .toList();
    }

}
