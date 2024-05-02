package com.hackaboss.PruebaTecnica4.service;

import com.hackaboss.PruebaTecnica4.dto.FlightBookingDTO;
import com.hackaboss.PruebaTecnica4.exception.FlightBookingException;
import com.hackaboss.PruebaTecnica4.model.Flight;
import com.hackaboss.PruebaTecnica4.model.FlightBooking;
import com.hackaboss.PruebaTecnica4.model.Person;
import com.hackaboss.PruebaTecnica4.repository.IFlightBookingRepository;
import com.hackaboss.PruebaTecnica4.repository.IFlightRepository;
import com.hackaboss.PruebaTecnica4.repository.IPersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class FlightBookingService implements IFlightBookingService{

    @Autowired
    private IFlightBookingRepository flightBookingRepo;
    @Autowired
    private IFlightRepository flightRepo;
    @Autowired
    private IFlightService flightService;

    @Autowired
    private IPersonRepository personRepo;

    @Override
    public FlightBooking saveFlightBooking(FlightBooking flightBooking) throws FlightBookingException{


        //verifico si hay vuelos disponibles con los datos seleccionados
        boolean isAvailableFlight = isAvailableFlight(flightBooking);

        if (isAvailableFlight) {

            double tickets = flightBooking.getPeopleQ() * flightBooking.getFlight().getFlightPrice();
            flightBooking.setTotalPrice(tickets);

            flightBooking.setStatus(true);
            return flightBookingRepo.save(flightBooking);
        }
        else {
            throw new FlightBookingException("No flights available for the selected dates and destination.");
        }

    }

    private boolean isAvailableFlight(FlightBooking flightBooking) throws FlightBookingException {

        boolean available = false;

        //si el usuario no cargo pasajeros en la reserva
        if(flightBooking.getPassengers().isEmpty()){
            throw new FlightBookingException("The list of passengers is empty.");
        }

        List<Flight> allFlights = flightRepo.getAllFlights();
        List<Flight> listAllFlightsByDateAndDestination = allFlights.stream()
                .filter(f->f.getDate().equals(flightBooking.getDate()))
                .filter(f->f.getOrigin().equals(flightBooking.getOrigin()))
                .filter(f->f.getDestination().equals(flightBooking.getDestination()))
                .filter(f->f.getSeatType().equals(flightBooking.getSeatType()))
                .filter(f->f.getIsComplete().equals(false))
                .toList();

        //por cada vuelo me fijo si hay asientos disponibles para la cant de personas
        for(Flight fli: listAllFlightsByDateAndDestination){
            if( fli.getSeatsAvailable() >= flightBooking.getPeopleQ()){

                flightBooking.setFlight(fli);
                //verifico si ya existe la reserva
                boolean existBooked = existFlightBooking(flightBooking);

                if (!existBooked) {
                    available = true;
                    //al reservar el vuelo cambia la cantidad de asientos disponibles del vuelo
                    List<Person> listPassengers = getPassengersFlightBooking(flightBooking);

                    return available;
                }
            }
        }
        return available;
    }
    private boolean existFlightBooking(FlightBooking flightBooking)
            throws FlightBookingException{

        List<Person> listPassengers = new ArrayList<>();
        List<Person> allPersons = personRepo.getAllPersons();
        List<Person> listPassengersSelected = flightBooking.getPassengers();
        boolean existBooked = false;

        for(Person pp: listPassengersSelected){

            Person pers = allPersons.stream()
                        .filter(p -> p.getDni().equals(pp.getDni()))
                        .findFirst()
                        .orElseThrow(() -> new FlightBookingException("The person with DNI: "+pp.getDni()+" is not registered."));
            listPassengers.add(pers);
            flightBooking.setPassengers(listPassengers);

            Person person = personRepo.findPersonById(pp.getDni());
            if(allPersons.contains(person)){
                List<FlightBooking> listBookings= person.getFlightBookings();
                FlightBooking booking = listBookings.stream()
                        .filter(f->f.getFlight().getFlightCode().equals(flightBooking.getFlight().getFlightCode()))
                        .findFirst()
                        .orElse(null);

                if(booking != null){
                    throw new FlightBookingException("Exist a booking for this passenger DNI: "+ person.getDni() );
                }
            } else {
                throw new FlightBookingException("The person is not registered.");
            }
        }
        return existBooked;
    }
    private List<Person> getPassengersFlightBooking(FlightBooking flightBooking) throws FlightBookingException{

        List<Person> listPassengers = new ArrayList<>();
        if(flightBooking.getPeopleQ() <= flightBooking.getFlight().getSeatsAvailable()){

            if(flightBooking.getPeopleQ().equals(flightBooking.getPassengers().size()) ){

                int seatsQBefore = flightBooking.getFlight().getSeatsAvailable();
                int seatsQAfter = seatsQBefore - flightBooking.getPeopleQ();
                Flight flightSelected = flightBooking.getFlight();
                flightSelected.setSeatsAvailable(seatsQAfter);

                if(flightSelected.getSeatsAvailable().equals(0)){
                    flightSelected.setIsComplete(true);
                }
                for(Person per: flightBooking.getPassengers()){
                    listPassengers.add(per);
                }
                return listPassengers;
            } else {
                throw new FlightBookingException("Number of persons is not correct.");
            }
        } else {
            throw new FlightBookingException("No seats quantity available.");
        }
    }

    @Override
    public List<FlightBookingDTO> getFlightBookings() {
        List<FlightBooking> listFlightBooking = flightBookingRepo.getFlightBookings();
        return listFlightBooking.stream()
                .map(this::toFlightBookingDTO)
                .toList();
    }

    private FlightBookingDTO toFlightBookingDTO(FlightBooking flightBooking) {
        FlightBookingDTO flightDTO = new FlightBookingDTO();
        flightDTO.setFlightCode(flightBooking.getFlight().getFlightCode());
        flightDTO.setDate(flightBooking.getDate());
        flightDTO.setOrigin(flightBooking.getOrigin());
        flightDTO.setDestination(flightBooking.getDestination());
        flightDTO.setSeatType(flightBooking.getSeatType());
        flightDTO.setTotalPrice(flightBooking.getTotalPrice());
        flightDTO.setPeopleQ(flightBooking.getPeopleQ());
        return flightDTO;
    }

    @Override
    public FlightBookingDTO findFlightBookingById(Long id) {
        FlightBooking findFB = flightBookingRepo.findFlightBookingById(id);
        if(findFB!=null){
            return toFlightBookingDTO(findFB);
        }
        return null;
    }

    @Override
    public FlightBooking editFlightBooking(Long id, FlightBooking flightBooking)
            throws FlightBookingException {
        FlightBooking flightBookingEdit = flightBookingRepo.findFlightBookingById(id);
        if (flightBookingEdit != null) {
            flightBookingEdit.setDate(flightBooking.getDate());
            flightBookingEdit.setOrigin(flightBooking.getOrigin());
            flightBookingEdit.setDestination(flightBooking.getDestination());
            flightBookingEdit.setSeatType(flightBooking.getSeatType());
            flightBookingEdit.setTotalPrice(flightBooking.getTotalPrice());
            flightBookingEdit.setPeopleQ(flightBooking.getPeopleQ());
            flightBookingEdit.setPassengers(flightBooking.getPassengers());
            return flightBookingRepo.save(flightBookingEdit);
        } else {
            throw new FlightBookingException("Flight Booking not found.");
        }
    }

    @Override
    public FlightBooking deleteFlightBooking(Long id) throws FlightBookingException{
        FlightBooking flightBookingDelete = flightBookingRepo.findFlightBookingById(id);
        if(flightBookingDelete != null) {
            //Al eliminar una reserva, se ponen disponibles los asientos de la reserva eliminada
            Flight fli = flightBookingDelete.getFlight();
            int seatsDelete = flightBookingDelete.getPeopleQ();
            int totalSeats = fli.getSeatsAvailable() + seatsDelete;
            fli.setSeatsAvailable(totalSeats);

            flightRepo.save(fli);

            flightBookingDelete.setPassengers(new ArrayList<>());
            flightBookingDelete.setStatus(false);
            return flightBookingRepo.save(flightBookingDelete);
        } else {
            throw new FlightBookingException("Flight Booking not found.");
        }
    }


}
