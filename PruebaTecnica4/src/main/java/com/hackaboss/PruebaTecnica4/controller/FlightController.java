package com.hackaboss.PruebaTecnica4.controller;

import com.hackaboss.PruebaTecnica4.dto.FlightDTO;
import com.hackaboss.PruebaTecnica4.model.Flight;
import com.hackaboss.PruebaTecnica4.service.IFlightService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@Tag(name = "Flight")
@RestController
@RequestMapping("/agency/flights")
public class FlightController {
    @Autowired
    private IFlightService flightService;


    @PostMapping("/new")
    @Operation(summary = "Agregar un vuelo")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "The operation was executed successfully."),
            @ApiResponse(responseCode = "400", description = "Some parameter does not comply with the format or is required and is not present."),
            @ApiResponse(responseCode = "500", description = "Internal server error.")
    })
    public ResponseEntity<String> createFlight(@RequestBody Flight flight){

        try {
            Flight addFlight = flightService.saveFlight(flight);
            if(addFlight !=null) {
                return new ResponseEntity<>("Flight created successfully.", HttpStatus.CREATED);
            } else {
                return new ResponseEntity<>("Flight already registered.", HttpStatus.BAD_REQUEST);
            }
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error to create. " + e.getMessage());
        }
    }
    @GetMapping("/all")
    @Operation(summary = "Ver todos los vuelos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "The operation was executed successfully."),
            @ApiResponse(responseCode = "400", description = "Some parameter does not comply with the format or is required and is not present."),
            @ApiResponse(responseCode = "500", description = "Internal server error.")
    })
    public List<FlightDTO> getAllFlights(){
        return flightService.getAllFlights();
    }


    @GetMapping
    @Operation(summary = "Obtener un vuelo por destino, fecha de ida y fecha de regreso")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "The operation was executed successfully."),
            @ApiResponse(responseCode = "400", description = "Some parameter does not comply with the format or is required and is not present."),
            @ApiResponse(responseCode = "500", description = "Internal server error.")
    })
    public ResponseEntity<?> getFlightByDestinationAndDate(@RequestParam LocalDate dateFrom,
                                                           @RequestParam LocalDate dateTo,
                                                      @RequestParam String origin,
                                                      @RequestParam String destination){

        try {
            List<FlightDTO> findListFlight  = flightService.getFlightByDestinationAndDate(dateFrom, dateTo, origin, destination);
            if (!findListFlight.isEmpty()) {
                return new ResponseEntity<>(findListFlight, HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Flight not found for date and destination selected.",HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar un vuelo por su id", description = "Id de tipo Long")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "The operation was executed successfully."),
            @ApiResponse(responseCode = "400", description = "Some parameter does not comply with the format or is required and is not present."),
            @ApiResponse(responseCode = "500", description = "Internal server error.")
    })
    public ResponseEntity<?> findFlightById(@PathVariable Long id){
        try {
            FlightDTO findFlight  = flightService.findFlightById(id);
            if (findFlight != null) {
                return new ResponseEntity<>(findFlight, HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Flight not found.",HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/edit/{id}")
    @Operation(summary = "Editar un vuelo",
            description = "Este método permite buscar un vuelo por su id y editarlo (id de tipo Long).")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "The operation was executed successfully."),
            @ApiResponse(responseCode = "400", description = "Some parameter does not comply with the format or is required and is not present."),
            @ApiResponse(responseCode = "500", description = "Internal server error.")
    })
    public ResponseEntity<String> editFlight(@PathVariable Long id, @RequestBody Flight flight){

        try{
            Flight editFlight = flightService.editFlight(id, flight);
            if (editFlight != null) {
                return ResponseEntity.ok("Flight edited successfully.");
            } else {
                return new ResponseEntity<>("Flight not found.", HttpStatus.NOT_FOUND);
            }
        } catch(Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error to edit. " + e.getMessage());
        }
    }

    @DeleteMapping("/delete/{id}")
    @Operation(summary = "Eliminar un vuelo por id", description = "Id de tipo Long")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "The operation was executed successfully."),
            @ApiResponse(responseCode = "400", description = "Some parameter does not comply with the format or is required and is not present."),
            @ApiResponse(responseCode = "500", description = "Internal server error.")
    })
    public ResponseEntity<String> deleteFlight(@PathVariable Long id){

        try{
            Flight flightDelete = flightService.deleteFlight(id);
            if (flightDelete != null) {
                return ResponseEntity.ok("Flight deleted successfully.");
            } else {
                return new ResponseEntity<>("Flight not found.", HttpStatus.NOT_FOUND);
            }
        } catch(Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error to delete. " + e.getMessage());
        }
    }


}
