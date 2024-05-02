package com.hackaboss.PruebaTecnica4.service;

import com.hackaboss.PruebaTecnica4.dto.FlightDTO;
import com.hackaboss.PruebaTecnica4.exception.FlightBookingException;
import com.hackaboss.PruebaTecnica4.model.Flight;
import com.hackaboss.PruebaTecnica4.model.FlightBooking;
import com.hackaboss.PruebaTecnica4.repository.IFlightBookingRepository;
import com.hackaboss.PruebaTecnica4.repository.IFlightRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class FlightService implements IFlightService{
    @Autowired
    private IFlightRepository flightRepo;
    @Autowired
    private IFlightBookingRepository flightBookingRepo;

    @Override
    public Flight saveFlight(Flight flight) {

        List<Flight> listFlights = flightRepo.getAllFlights();
        Flight addFlight = listFlights.stream()
                .filter(f -> f.getFlightCode().equals(flight.getFlightCode()))
                .filter(f -> f.getSeatType().equals(flight.getSeatType()))
                .findFirst().orElse(null);
        if(addFlight != null){
            return null;
        }
        flight.setSeatsAvailable(flight.getSeatsQ());
        flight.setIsComplete(false);
        flight.setStatus(true);
        return flightRepo.save(flight);
    }

    @Override
    public List<FlightDTO> getAllFlights() {
        List<Flight> listFlights = flightRepo.getAllFlights();
        return listFlights.stream()
                .map(this::toFlightDTO)
                .toList();
    }

    private FlightDTO toFlightDTO(Flight flight) {
        FlightDTO flightDTO = new FlightDTO();
        flightDTO.setFlightCode(flight.getFlightCode());
        flightDTO.setDate(flight.getDate());
        flightDTO.setOrigin(flight.getOrigin());
        flightDTO.setDestination(flight.getDestination());
        flightDTO.setSeatsQ(flight.getSeatsQ());
        flightDTO.setSeatsAvailable(flight.getSeatsAvailable());
        flightDTO.setSeatType(flight.getSeatType());
        flightDTO.setFlightPrice(flight.getFlightPrice());
        flightDTO.setIsComplete(flight.getIsComplete());
        return flightDTO;
    }

    @Override
    public FlightDTO findFlightById(Long id) {
        Flight findFlight = flightRepo.findFlightById(id);
        if(findFlight!=null){
            return toFlightDTO(findFlight);
        }
        return null;
    }

    @Override
    public Flight editFlight(Long id, Flight flight) {
        Flight editFlight = flightRepo.findFlightById(id);

        if (editFlight != null){
            editFlight.setFlightCode(flight.getFlightCode());
            editFlight.setDate(flight.getDate());
            editFlight.setOrigin(flight.getOrigin());
            editFlight.setDestination(flight.getDestination());
            editFlight.setFlightPrice(flight.getFlightPrice());
            editFlight.setSeatType(flight.getSeatType());
            editFlight.setFlightPrice(flight.getFlightPrice());
            editFlight.setSeatsQ(flight.getSeatsQ());
            editFlight.setSeatsAvailable(flight.getSeatsAvailable());
            editFlight.setIsComplete(flight.getIsComplete());
            return flightRepo.save(editFlight);
        }
        return null;
    }

    @Override
    public Flight deleteFlight(Long id) throws FlightBookingException {
        Flight deleteFlight = flightRepo.findFlightById(id);

        if (deleteFlight != null){
            //verifico que no exista una reserva asociada al codigo de vuelo
            List<FlightBooking> listFB= flightBookingRepo.getFlightBookings();
            if (!listFB.isEmpty()) {
                boolean hasFlightBooking = listFB.stream()
                        .anyMatch(fb -> fb.getFlight().getFlightCode().equals(deleteFlight.getFlightCode()));
                if (hasFlightBooking) {
                    throw new FlightBookingException("Flight booking linked to this flight code. Please delete the flight booking first.");
                }
            }
            deleteFlight.setStatus(false);
            return flightRepo.save(deleteFlight);
        }
        return null;

    }

    @Override
    public List<FlightDTO> getFlightByDestinationAndDate(LocalDate dateOrigin, LocalDate dateReturn,
                                                    String origin, String destination) {
        List<Flight> listFlightsOrigin = flightRepo.getAllFlights().stream()
                .filter(f->f.getDate().equals(dateOrigin))
                .filter(f->f.getOrigin().equals(origin))
                .filter(f->f.getDestination().equals(destination))
                .filter(f->f.getIsComplete().equals(false))
                .toList();

        List<Flight> listFlightsReturn = flightRepo.getAllFlights().stream()
                .filter(f->f.getDate().equals(dateReturn))
                .filter(f->f.getOrigin().equals(destination))
                .filter(f->f.getDestination().equals(origin))
                .filter(f->f.getIsComplete().equals(false))
                .toList();

        List<Flight> listAllFlightsSelected = new ArrayList<>();
        listAllFlightsSelected.addAll(listFlightsOrigin);
        listAllFlightsSelected.addAll(listFlightsReturn);

        return listAllFlightsSelected.stream()
                .map(this::toFlightDTO)
                .toList();
    }
}
