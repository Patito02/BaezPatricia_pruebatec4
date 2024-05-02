package com.hackaboss.PruebaTecnica4.service;

import com.hackaboss.PruebaTecnica4.dto.FlightBookingDTO;
import com.hackaboss.PruebaTecnica4.exception.FlightBookingException;
import com.hackaboss.PruebaTecnica4.model.FlightBooking;
import com.hackaboss.PruebaTecnica4.model.Person;

import java.util.List;

public interface IFlightBookingService {
    FlightBooking saveFlightBooking(FlightBooking flightBooking) throws FlightBookingException;

    List<FlightBookingDTO> getFlightBookings();

    FlightBookingDTO findFlightBookingById(Long id) throws FlightBookingException;

    FlightBooking editFlightBooking(Long id, FlightBooking flightBooking) throws FlightBookingException;

    FlightBooking deleteFlightBooking(Long id) throws FlightBookingException;


}
