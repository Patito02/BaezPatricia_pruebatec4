package com.hackaboss.PruebaTecnica4.service;

import com.hackaboss.PruebaTecnica4.dto.RoomBookingDTO;
import com.hackaboss.PruebaTecnica4.exception.RoomBookingException;
import com.hackaboss.PruebaTecnica4.model.Person;
import com.hackaboss.PruebaTecnica4.model.Room;
import com.hackaboss.PruebaTecnica4.model.RoomBooking;
import com.hackaboss.PruebaTecnica4.repository.IHotelRepository;
import com.hackaboss.PruebaTecnica4.repository.IPersonRepository;
import com.hackaboss.PruebaTecnica4.repository.IRoomBookingRepository;
import com.hackaboss.PruebaTecnica4.repository.IRoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class RoomBookingService implements IRoomBookingService {

    @Autowired
    private IRoomBookingRepository roomBookingRepo;
    @Autowired
    private IHotelRepository hotelRepo;
    @Autowired
    private IRoomRepository roomRepo;
    @Autowired
    private IPersonRepository personRepo;
    @Autowired
    private IRoomService roomService;

    @Override
    public RoomBooking saveRoomBooking(RoomBooking roomBooking) throws RoomBookingException{

        //verifico si hay habitacion disponible con los datos seleccionados
        boolean isAvailableRoom = isAvailableRoom(roomBooking);

        //verifico que la cantidad de personas sea válida
        List<Person> listHosts = getHostRoomBooking(roomBooking.getPeopleQ(), roomBooking.getHosts(),
                roomBooking.getRoomType());
        roomBooking.setHosts(listHosts);

        if (isAvailableRoom) {

            double priceNight = roomBooking.getRoom().getRoomPrice();
            int nights = roomBooking.getDateTo().getDayOfMonth() - roomBooking.getDateFrom().getDayOfMonth();
            double totalPrice = priceNight * nights;
            roomBooking.setNights(nights);
            roomBooking.setTotalPrice(totalPrice);

            roomBooking.getRoom().setIsBooked(true);

            roomBooking.setStatus(true);
            return roomBookingRepo.save(roomBooking);

        } else {
            throw new RoomBookingException("No rooms available for the selected dates.");
        }
    }
    private boolean isAvailableRoom(RoomBooking roomBooking) throws RoomBookingException{

        boolean available = true;

        List<Room> listRoomsByTypeAndPlace = getRoomsByTypeAndPlace(roomBooking);
        if(listRoomsByTypeAndPlace.isEmpty()){
            throw new RoomBookingException("No rooms of the selected type in that destination.");
        }

        List<RoomBookingDTO> listRBTypeAndPlace = getRoomBookingsByRoomTypeAndPlace(roomBooking);

        if (listRBTypeAndPlace.isEmpty()) {
            //si la lista de roomBookings esta vacia, busco la primer habitacion de la lista y
            // seteo el id
            Long roomIdByPlace = listRoomsByTypeAndPlace.stream()
                    .map(Room::getId)
                    .findFirst()
                    .orElse(null);
            if (roomIdByPlace != null) {
                Room roomFind = roomRepo.findRoomById(roomIdByPlace);
                roomBooking.setRoom(roomFind);
                available = true;
            }
        } else {
            //si la lista de roombookings tiene reservas, verifico si ya existe la que deseo cargar
            available = existRoomBooking(roomBooking, listRoomsByTypeAndPlace, listRBTypeAndPlace);
            if(available == false){
                throw new RoomBookingException("Exist a booking.");
            }
        }
        return available;
    }

    private boolean existRoomBooking(RoomBooking roomBooking, List<Room> listRoomsByTypeAndPlace,
                                     List<RoomBookingDTO> listRBTypeAndPlace) {
        LocalDate dateFrom = roomBooking.getDateFrom();
        LocalDate dateTo = roomBooking.getDateTo();

        //busco si la roomBooking de cada habitacion esta vacia o si NO hay coincidencia
        // de las fechas y roomCode y seteo la room con su id
        // true -> no exite roombooking identica, hay habitaciones disponibles, seteo el id
        return  listRoomsByTypeAndPlace.stream()
                .filter(room -> room.getRoomBookings().isEmpty() ||
                        listRBTypeAndPlace.stream().noneMatch(roomBook ->
                                dateFrom.isBefore(roomBook.getDateTo()) &&
                                        dateTo.isAfter(roomBook.getDateFrom()) &&
                                        roomBook.getRoomCode().equals(room.getRoomCode())))
                .findFirst()
                .map(room -> {
                    roomBooking.setRoom(room);
                    return true;
                })
                .orElse(false);

    }

    private List<Room> getRoomsByTypeAndPlace(RoomBooking roomBooking) {
        List<Room> listRoomTypeSelected = roomService.getRoomsByType(roomBooking.getRoomType());

        return listRoomTypeSelected.stream()
                .filter(r->r.getHotel().getPlace().equals(roomBooking.getPlace()))
                .toList();
    }
    private List<RoomBookingDTO> getRoomBookingsByRoomTypeAndPlace(RoomBooking roomBooking) {
        List<RoomBookingDTO> listRoomBooking = getRoomBookings();

        return listRoomBooking.stream()
                .filter(rb->rb.getRoomType().equals(roomBooking.getRoomType()))
                .filter(rb->rb.getPlace().equals(roomBooking.getPlace()))
                .toList();
    }
    private List<Person> getHostRoomBooking(Integer peopleQ, List<Person> hosts, String roomType)
            throws RoomBookingException{
        List<Person> listHost = new ArrayList<>();
        List<Person> allPersons = personRepo.getAllPersons();
        int cantPersonas = switch (roomType) {
            case "Single" -> 1;
            case "Double" -> 2;
            case "Triple" -> 3;
            case "Multiple" -> 4;
            default -> throw new RoomBookingException("Exceeds the number of people allowed for room.");
        };
        //comparo la peopleQ con el roomType y la lista de hosts ingresadas
        if(peopleQ.equals(cantPersonas) && peopleQ.equals(hosts.size())){
            for (Person person : hosts) {
                Person pers = allPersons.stream()
                        .filter(p -> p.getDni().equals(person.getDni()))
                        .findFirst()
                        .orElseThrow(() -> new RoomBookingException("The person with DNI " + person.getDni() + " is not registered."));
                listHost.add(pers);
            }
            return listHost;
        } else {
            throw new RoomBookingException("Number of persons is not correct for roomType selected.");
        }
    }

    @Override
    public List<RoomBookingDTO> getRoomBookings() {
        List<RoomBooking> listRoomBooking = roomBookingRepo.getRoomBookings();
        return listRoomBooking.stream()
                .map(this::toRoomBookingDTO)
                .toList();
    }
    private RoomBookingDTO toRoomBookingDTO(RoomBooking roomBooking) {
        RoomBookingDTO bookingDTO = new RoomBookingDTO();
        bookingDTO.setRoomType(roomBooking.getRoomType());
        bookingDTO.setPeopleQ(roomBooking.getPeopleQ());
        bookingDTO.setDateFrom(roomBooking.getDateFrom());
        bookingDTO.setDateTo(roomBooking.getDateTo());
        bookingDTO.setNights(roomBooking.getNights());
        bookingDTO.setPlace(roomBooking.getRoom().getHotel().getPlace());
        bookingDTO.setHotelCode(roomBooking.getRoom().getHotel().getHotelCode());
        bookingDTO.setTotalPrice(roomBooking.getTotalPrice());
        bookingDTO.setRoomCode(roomBooking.getRoom().getRoomCode());
        return bookingDTO;
    }
    @Override
    public RoomBookingDTO findRoomBookingById(Long id) {
        RoomBooking findRB = roomBookingRepo.findRoomBookingById(id);
        if (findRB != null) {
            return toRoomBookingDTO(findRB);
        } else {
            return null;
        }
    }

    @Override
    public RoomBooking editRoomBooking(Long id, RoomBooking roomBooking) {
        RoomBooking editBooking = roomBookingRepo.findRoomBookingById(id);
        if (editBooking != null) {
            editBooking.setRoomType(roomBooking.getRoomType());
            editBooking.setPeopleQ(roomBooking.getPeopleQ());
            editBooking.setDateFrom(roomBooking.getDateFrom());
            editBooking.setDateTo(roomBooking.getDateTo());
            editBooking.setPlace(roomBooking.getPlace());
            editBooking.setTotalPrice(roomBooking.getTotalPrice());
            editBooking.setNights(roomBooking.getNights());
            editBooking.setHosts(roomBooking.getHosts());
            return roomBookingRepo.save(editBooking);
        }
        return null;
    }

    @Override
    public RoomBooking deleteRoomBooking(Long id) {
        RoomBooking deleteBooking = roomBookingRepo.findRoomBookingById(id);
        if (deleteBooking != null) {
            List<Person> listPersons = new ArrayList<>();
            deleteBooking.setHosts(listPersons);
            deleteBooking.setStatus(false);
            deleteBooking.getRoom().setIsBooked(false);
            return roomBookingRepo.save(deleteBooking);
        }
        return null;
    }



}
