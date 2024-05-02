package com.hackaboss.PruebaTecnica4.service;

import com.hackaboss.PruebaTecnica4.dto.PersonDTO;
import com.hackaboss.PruebaTecnica4.exception.FlightBookingException;
import com.hackaboss.PruebaTecnica4.exception.RoomBookingException;
import com.hackaboss.PruebaTecnica4.model.FlightBooking;
import com.hackaboss.PruebaTecnica4.model.Person;
import com.hackaboss.PruebaTecnica4.model.RoomBooking;
import com.hackaboss.PruebaTecnica4.repository.IFlightBookingRepository;
import com.hackaboss.PruebaTecnica4.repository.IPersonRepository;
import com.hackaboss.PruebaTecnica4.repository.IRoomBookingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PersonService implements IPersonService{

    @Autowired
    private IPersonRepository personRepo;
    @Autowired
    private IFlightBookingRepository flightBookingRepo;
    @Autowired
    private IRoomBookingRepository roomBookingRepo;

    @Override
    public Person savePerson(Person person) {

        Person existPerson = personRepo.findById(person.getDni()).orElse(null);
        if (existPerson != null) {
            return null;
        }
        person.setStatus(true);
        return personRepo.save(person);
    }

    @Override
    public List<PersonDTO> getAllPersons() {
        List<Person> listPersons = personRepo.getAllPersons();
        return listPersons.stream()
                .map(this::toPersonDTO)
                .toList();
    }

    private PersonDTO toPersonDTO(Person person) {
        PersonDTO persDTO = new PersonDTO();
        persDTO.setDni(person.getDni());
        persDTO.setName(person.getName());
        persDTO.setLastName(person.getLastName());
        persDTO.setEmail(person.getEmail());
        return persDTO;
    }

    @Override
    public PersonDTO findPersonById(String dni) {
        Person findPerson = personRepo.findPersonById(dni);
        if(findPerson!= null){
            return toPersonDTO(findPerson);
        }
        return null;
    }

    @Override
    public Person editPerson(String dni, Person person) {
        Person editPerson = personRepo.findById(dni).orElse(null);

        if (editPerson != null) {
            editPerson.setName(person.getName());
            editPerson.setLastName(person.getLastName());
            editPerson.setEmail(person.getEmail());
            return personRepo.save(editPerson);
        }
        return null;
    }

    @Override
    public Person deletePerson(String dni) throws FlightBookingException, RoomBookingException {
        Person deletePerson = personRepo.findById(dni).orElse(null);

        if (deletePerson != null) {
            //verifico que no exista una roomBooking o flightBooking asociada a la persona
            List<FlightBooking> listFB= flightBookingRepo.getFlightBookings();
            if (!listFB.isEmpty()) {
                boolean hasFlightBooking = listFB.stream()
                        .anyMatch(fb -> fb.getPassengers().contains(deletePerson));
                if (hasFlightBooking) {
                    throw new FlightBookingException("Flight booking linked to this dni. Please delete the flight booking first.");
                }
            }
            List<RoomBooking> listRB= roomBookingRepo.getRoomBookings();
            if (!listRB.isEmpty()) {
                boolean hasRoomBooking = listRB.stream()
                        .anyMatch(fb -> fb.getHosts().contains(deletePerson));
                if (hasRoomBooking) {
                    throw new RoomBookingException("Room booking linked to this dni. Please delete the room booking first.");
                }
            }
            deletePerson.setStatus(false);
            personRepo.save(deletePerson);
            return deletePerson;
        }
        return null;

    }
}
