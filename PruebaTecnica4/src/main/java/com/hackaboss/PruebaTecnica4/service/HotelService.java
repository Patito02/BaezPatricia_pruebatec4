package com.hackaboss.PruebaTecnica4.service;

import com.hackaboss.PruebaTecnica4.dto.HotelDTO;
import com.hackaboss.PruebaTecnica4.exception.RoomBookingException;
import com.hackaboss.PruebaTecnica4.model.Hotel;
import com.hackaboss.PruebaTecnica4.model.RoomBooking;
import com.hackaboss.PruebaTecnica4.repository.IHotelRepository;
import com.hackaboss.PruebaTecnica4.repository.IRoomBookingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HotelService implements IHotelService{
    @Autowired
    private IHotelRepository hotelRepo;
    @Autowired
    private IRoomBookingRepository roomBookingRepo;

    @Override
    public Hotel saveHotel(Hotel hotel) {
        Hotel addHotel = hotelRepo.findHotelById(hotel.getHotelCode());
        if (addHotel != null){
            return null;
        }
        hotel.setStatus(true);
        return hotelRepo.save(hotel);
    }

    @Override
    public List<HotelDTO> getAllHotels() {
        List<Hotel> listHotels = hotelRepo.getAllHotels();
        return listHotels.stream()
                .map(this::toHotelDTO)
                .toList();
    }

    private HotelDTO toHotelDTO(Hotel hotel) {
        HotelDTO hotelDTO = new HotelDTO();
        hotelDTO.setHotelCode(hotel.getHotelCode());
        hotelDTO.setName(hotel.getName());
        hotelDTO.setPlace(hotel.getPlace());
        return hotelDTO;
    }

    @Override
    public HotelDTO findHotelById(String hotelCode) {
        Hotel findHotel = hotelRepo.findHotelById(hotelCode);
        if(findHotel!=null){
            return toHotelDTO(findHotel);
        }
        return null;
    }

    @Override
    public Hotel editHotel(String hotelCode, Hotel hotel) {

        Hotel editHotel = hotelRepo.findById(hotelCode).orElse(null);

        if (editHotel != null){
            editHotel.setName(hotel.getName());
            editHotel.setPlace(hotel.getPlace());
            return hotelRepo.save(editHotel);
        }
        return null;
    }

    @Override
    public Hotel deleteHotel(String hotelCode) throws RoomBookingException {
        Hotel deleteHotel = hotelRepo.findHotelById(hotelCode);

        if (deleteHotel != null){
            //verifico que no exista una reserva asociada al codigo de hotel
            List<RoomBooking> listRB= roomBookingRepo.getRoomBookings();
            if (!listRB.isEmpty()) {
                boolean hasRoomBooking = listRB.stream()
                        .anyMatch(fb -> fb.getRoom().getHotel().getHotelCode().equals(hotelCode));
                if (hasRoomBooking) {
                    throw new RoomBookingException("Room booking linked to this room code. Please delete the room booking first.");
                }
            }
            deleteHotel.setStatus(false);
            return hotelRepo.save(deleteHotel);
        }
        return null;
    }
}
