package com.hackaboss.PruebaTecnica4.controller;

import com.hackaboss.PruebaTecnica4.dto.FlightBookingDTO;
import com.hackaboss.PruebaTecnica4.exception.FlightBookingException;
import com.hackaboss.PruebaTecnica4.model.FlightBooking;
import com.hackaboss.PruebaTecnica4.service.IFlightBookingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Flight Booking")
@RestController
@RequestMapping("/agency/flight-booking")
public class FlightBookingController {

    @Autowired
    private IFlightBookingService flightBookingService;

    @PostMapping("/new")
    @Operation(summary = "Agregar una reserva de vuelo")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "The operation was executed successfully."),
            @ApiResponse(responseCode = "400", description = "Some parameter does not comply with the format or is required and is not present."),
            @ApiResponse(responseCode = "500", description = "Internal server error.")
    })
    public ResponseEntity<String> createFlightBooking(@RequestBody FlightBooking flightBooking){

        try {
            FlightBooking addBooking = flightBookingService.saveFlightBooking(flightBooking);

            return new ResponseEntity<>("The total price of the booking is: " +
                    addBooking.getTotalPrice() + " €", HttpStatus.CREATED);

        } catch (FlightBookingException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/all")
    @Operation(summary = "Ver todas las reservas de vuelos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "The operation was executed successfully."),
            @ApiResponse(responseCode = "400", description = "Some parameter does not comply with the format or is required and is not present."),
            @ApiResponse(responseCode = "500", description = "Internal server error.")
    })
    public List<FlightBookingDTO> getFlightBookings (){
        return flightBookingService.getFlightBookings();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar una reserva de vuelo por su id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "The operation was executed successfully."),
            @ApiResponse(responseCode = "400", description = "Some parameter does not comply with the format or is required and is not present."),
            @ApiResponse(responseCode = "500", description = "Internal server error.")
    })
    public ResponseEntity<?> findFlightBookingById(@PathVariable Long id) {

        try {
            FlightBookingDTO findBooking  = flightBookingService.findFlightBookingById(id);
            if (findBooking != null) {
                return new ResponseEntity<>(findBooking, HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Flight not found.",HttpStatus.NOT_FOUND);
            }
        } catch (FlightBookingException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/edit/{id}")
    @Operation(summary = "Editar la reserva de vuelo",
    description = "Este método permite buscar una reseva de vuelo por su id y editarla.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "The operation was executed successfully."),
            @ApiResponse(responseCode = "400", description = "Some parameter does not comply with the format or is required and is not present."),
            @ApiResponse(responseCode = "500", description = "Internal server error.")
    })
    public ResponseEntity<String> editFlightBooking(@PathVariable Long id,
                                                    @RequestBody FlightBooking flightBooking){

        try{
            FlightBooking editBooking = flightBookingService.editFlightBooking(id, flightBooking);
            if (editBooking != null) {
                return ResponseEntity.ok("Flight Booking edited successfully.");
            } else {
                return new ResponseEntity<>("Flight Booking not found.", HttpStatus.NOT_FOUND);
            }
        } catch(Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error to edit. " + e.getMessage());
        }
    }

    @DeleteMapping("/delete/{id}")
    @Operation(summary = "Eliminar una reserva de vuelo por id",
            description = "Este método permite eliminar una reseva de vuelo por su id (id de tipo Long)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "The operation was executed successfully."),
            @ApiResponse(responseCode = "400", description = "Some parameter does not comply with the format or is required and is not present."),
            @ApiResponse(responseCode = "500", description = "Internal server error.")
    })
    public ResponseEntity<String> deleteFlightBooking (@PathVariable Long id){

        try{
            FlightBooking deleteBooking = flightBookingService.deleteFlightBooking(id);
            if (deleteBooking != null) {
                return ResponseEntity.ok("FlightBooking deleted successfully.");
            } else {
                return new ResponseEntity<>("FlightBooking not found.", HttpStatus.NOT_FOUND);
            }
        } catch(Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error to delete. " + e.getMessage());
        }
    }

}
