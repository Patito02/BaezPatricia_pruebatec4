package com.hackaboss.PruebaTecnica4.controller;

import com.hackaboss.PruebaTecnica4.dto.HotelDTO;
import com.hackaboss.PruebaTecnica4.model.Hotel;
import com.hackaboss.PruebaTecnica4.service.IHotelService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Hotel")
@RestController
@RequestMapping("/agency/hotels")
public class HotelController {
    @Autowired
    private IHotelService hotelService;

    @PostMapping("/new")
    @Operation(summary = "Agregar un hotel")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "The operation was executed successfully."),
            @ApiResponse(responseCode = "400", description = "Some parameter does not comply with the format or is required and is not present."),
            @ApiResponse(responseCode = "500", description = "Internal server error.")
    })
    public ResponseEntity<String> createHotel(@RequestBody Hotel hotel){

        try {
            Hotel addHotel = hotelService.saveHotel(hotel);
            if(addHotel !=null) {
                return new ResponseEntity<>("Hotel created successfully.", HttpStatus.CREATED);
            } else {
                return new ResponseEntity<>("Hotel already registered.", HttpStatus.BAD_REQUEST);
            }
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error to create. " + e.getMessage());
        }
    }

    @GetMapping
    @Operation(summary = "Ver todos los hoteles")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "The operation was executed successfully."),
            @ApiResponse(responseCode = "400", description = "Some parameter does not comply with the format or is required and is not present."),
            @ApiResponse(responseCode = "500", description = "Internal server error.")
    })
    public List<HotelDTO> getAllHotels(){
        return hotelService.getAllHotels();
    }

    @GetMapping("/{hotelCode}")
    @Operation(summary = "Buscar un hotel por su código")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "The operation was executed successfully."),
            @ApiResponse(responseCode = "400", description = "Some parameter does not comply with the format or is required and is not present."),
            @ApiResponse(responseCode = "500", description = "Internal server error.")
    })
    public ResponseEntity<?> findHotelById(@PathVariable String hotelCode){

        try {
            HotelDTO findHotel  = hotelService.findHotelById(hotelCode);
            if (findHotel != null) {
                return new ResponseEntity<>(findHotel, HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Hotel not found.",HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/edit/{hotelCode}")
    @Operation(summary = "Editar un hotel",
            description = "Este método permite buscar un hotel por su código y editarlo.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "The operation was executed successfully."),
            @ApiResponse(responseCode = "400", description = "Some parameter does not comply with the format or is required and is not present."),
            @ApiResponse(responseCode = "500", description = "Internal server error.")
    })
    public ResponseEntity<String> editHotel(@PathVariable String hotelCode, @RequestBody Hotel hotel){
        try{
            Hotel editHotel = hotelService.editHotel(hotelCode, hotel);
            if (editHotel != null) {
                return ResponseEntity.ok("Hotel edited successfully.");
            } else {
                return new ResponseEntity<>("Hotel not found.", HttpStatus.NOT_FOUND);
            }
        } catch(Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error to edit. " + e.getMessage());
        }
    }

    @DeleteMapping("/delete/{hotelCode}")
    @Operation(summary = "Eliminar un hotel por código")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "The operation was executed successfully."),
            @ApiResponse(responseCode = "400", description = "Some parameter does not comply with the format or is required and is not present."),
            @ApiResponse(responseCode = "500", description = "Internal server error.")
    })
    public ResponseEntity<String> deleteHotel(@PathVariable String hotelCode){

        try{
            Hotel deleteHotel = hotelService.deleteHotel(hotelCode);
            if (deleteHotel != null) {
                return ResponseEntity.ok("Hotel deleted successfully.");
            } else {
                return new ResponseEntity<>("Hotel not found.", HttpStatus.NOT_FOUND);
            }
        } catch(Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error to delete. " + e.getMessage());
        }
    }

}
