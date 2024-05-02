package com.hackaboss.PruebaTecnica4.service;

import com.hackaboss.PruebaTecnica4.dto.PersonDTO;
import com.hackaboss.PruebaTecnica4.exception.FlightBookingException;
import com.hackaboss.PruebaTecnica4.exception.RoomBookingException;
import com.hackaboss.PruebaTecnica4.model.Person;

import java.util.List;

public interface IPersonService {
    Person savePerson(Person person);
    List<PersonDTO> getAllPersons();
    PersonDTO findPersonById(String dni);
    Person editPerson(String dni, Person person);
    Person deletePerson(String dni) throws FlightBookingException, RoomBookingException;

}
