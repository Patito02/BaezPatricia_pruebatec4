package com.hackaboss.PruebaTecnica4.service;

import com.hackaboss.PruebaTecnica4.dto.FlightDTO;
import com.hackaboss.PruebaTecnica4.exception.FlightBookingException;
import com.hackaboss.PruebaTecnica4.model.Flight;

import java.time.LocalDate;
import java.util.List;

public interface IFlightService {
    Flight saveFlight(Flight flight);
    List<FlightDTO> getAllFlights();
    FlightDTO findFlightById(Long id);
    Flight editFlight(Long id, Flight flight);
    Flight deleteFlight(Long id) throws FlightBookingException;

    List<FlightDTO> getFlightByDestinationAndDate(LocalDate dateOrigin, LocalDate dateReturn,
                                             String origin, String destination);
}
