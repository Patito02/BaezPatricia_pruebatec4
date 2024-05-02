package com.hackaboss.PruebaTecnica4.controller;

import com.hackaboss.PruebaTecnica4.dto.PersonDTO;
import com.hackaboss.PruebaTecnica4.exception.FlightBookingException;
import com.hackaboss.PruebaTecnica4.exception.RoomBookingException;
import com.hackaboss.PruebaTecnica4.model.Person;
import com.hackaboss.PruebaTecnica4.service.IPersonService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Person")
@RestController
@RequestMapping("/agency/persons")
public class PersonController {

    @Autowired
    private IPersonService personService;


    @PostMapping("/new")
    @Operation(summary = "Agregar una persona")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "The operation was executed successfully."),
            @ApiResponse(responseCode = "400", description = "Some parameter does not comply with the format or is required and is not present."),
            @ApiResponse(responseCode = "500", description = "Internal server error.")
    })
    public ResponseEntity<String> createPerson ( @RequestBody Person person){

        try {
            Person addPerson = personService.savePerson(person);
            if(addPerson !=null) {
                return new ResponseEntity<>("Person created successfully ", HttpStatus.CREATED);
            } else {
                return new ResponseEntity<>("Already registered person", HttpStatus.BAD_REQUEST);
            }
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error to create" + e.getMessage());
        }
    }

    @GetMapping("/all")
    @Operation(summary = "Ver todas las personas")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "The operation was executed successfully."),
            @ApiResponse(responseCode = "400", description = "Some parameter does not comply with the format or is required and is not present."),
            @ApiResponse(responseCode = "500", description = "Internal server error.")
    })
    public List<PersonDTO> getAllPersons(){
        return personService.getAllPersons();
    }


    @GetMapping("/{dni}")
    @Operation(summary = "Buscar una persona por su dni")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "The operation was executed successfully."),
            @ApiResponse(responseCode = "400", description = "Some parameter does not comply with the format or is required and is not present."),
            @ApiResponse(responseCode = "500", description = "Internal server error.")
    })
    public ResponseEntity<?> findPersonById(@PathVariable String dni){

        try {
            PersonDTO findPerson = personService.findPersonById(dni);
            if (findPerson != null) {
                return new ResponseEntity<>(findPerson, HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Person not found.",HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/edit/{dni}")
    @Operation(summary = "Editar una persona",
            description = "Este método permite buscar una persona por su dni y editarla.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "The operation was executed successfully."),
            @ApiResponse(responseCode = "400", description = "Some parameter does not comply with the format or is required and is not present."),
            @ApiResponse(responseCode = "500", description = "Internal server error.")
    })
    public ResponseEntity<String> editPerson(@PathVariable String dni, @RequestBody Person person){

        try {
            Person editPerson = personService.editPerson(dni,person);
            if (editPerson != null) {
                return ResponseEntity.ok("Person edited successfully.");
            } else {
                return new ResponseEntity<>("Person not found.", HttpStatus.NOT_FOUND);
            }
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error to edit. " + e.getMessage());
        }

    }

    @DeleteMapping("/delete/{dni}")
    @Operation(summary = "Eliminar una persona por dni")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "The operation was executed successfully."),
            @ApiResponse(responseCode = "400", description = "Some parameter does not comply with the format or is required and is not present."),
            @ApiResponse(responseCode = "500", description = "Internal server error.")
    })
    public ResponseEntity<String> deletePerson(@PathVariable String dni){

        try {
            Person deletePerson = personService.deletePerson(dni);
            if (deletePerson != null) {
                return ResponseEntity.ok("Person deleted successfully.");
            } else {
                return new ResponseEntity<>("Person not found.", HttpStatus.NOT_FOUND);
            }
        } catch (RoomBookingException | FlightBookingException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error to delete. " + e.getMessage());
        }
    }

}
