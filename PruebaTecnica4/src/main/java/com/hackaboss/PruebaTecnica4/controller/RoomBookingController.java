package com.hackaboss.PruebaTecnica4.controller;

import com.hackaboss.PruebaTecnica4.dto.RoomBookingDTO;
import com.hackaboss.PruebaTecnica4.exception.RoomBookingException;
import com.hackaboss.PruebaTecnica4.model.RoomBooking;
import com.hackaboss.PruebaTecnica4.service.IRoomBookingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Room Booking")
@RestController
@RequestMapping("/agency/room-booking")
public class RoomBookingController {

    @Autowired
    private IRoomBookingService roomBookingService;

    @PostMapping("/new")
    @Operation(summary = "Agregar una reserva de habitación")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "The operation was executed successfully."),
            @ApiResponse(responseCode = "400", description = "Some parameter does not comply with the format or is required and is not present."),
            @ApiResponse(responseCode = "500", description = "Internal server error.")
    })
    public ResponseEntity<String> createRoomBooking(@RequestBody RoomBooking roomBooking){

        try {
            RoomBooking addBooking = roomBookingService.saveRoomBooking(roomBooking);

            return new ResponseEntity<>("The total price of the booking is: " +
                        addBooking.getTotalPrice() + " €", HttpStatus.CREATED);

        } catch (RoomBookingException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/all")
    @Operation(summary = "Ver todas las reservas de habitaciones")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "The operation was executed successfully."),
            @ApiResponse(responseCode = "400", description = "Some parameter does not comply with the format or is required and is not present."),
            @ApiResponse(responseCode = "500", description = "Internal server error.")
    })
    public List<RoomBookingDTO> getRoomBookings (){
        return roomBookingService.getRoomBookings();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar una reserva de habitación por su id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "The operation was executed successfully."),
            @ApiResponse(responseCode = "400", description = "Some parameter does not comply with the format or is required and is not present."),
            @ApiResponse(responseCode = "500", description = "Internal server error.")
    })
    public ResponseEntity<?> findRoomBookingById( @PathVariable Long id) {

        try {
            RoomBookingDTO findBooking = roomBookingService.findRoomBookingById(id);
            if (findBooking != null) {
                return new ResponseEntity<>(findBooking, HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Room booking not found.",HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/edit/{id}")
    @Operation(summary = "Editar la reserva de habitación",
            description = "Este método permite buscar una reseva de habitación por su id y editarla.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "The operation was executed successfully."),
            @ApiResponse(responseCode = "400", description = "Some parameter does not comply with the format or is required and is not present."),
            @ApiResponse(responseCode = "500", description = "Internal server error.")
    })
    public ResponseEntity<String> editRoomBooking(@PathVariable Long id,
                                                  @RequestBody RoomBooking roomBooking){

        try{
            RoomBooking editBooking = roomBookingService.editRoomBooking(id, roomBooking);
            if (editBooking != null) {
                return ResponseEntity.ok("Room booking edited successfully.");
            } else {
                return new ResponseEntity<>("Room booking not found.", HttpStatus.NOT_FOUND);
            }
        } catch(Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error to edit. " + e.getMessage());
        }
    }

    @DeleteMapping("/delete/{id}")
    @Operation(summary = "Eliminar una reserva de habitación por id",
            description = "Este método permite eliminar una reseva de habitación por su id.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "The operation was executed successfully."),
            @ApiResponse(responseCode = "400", description = "Some parameter does not comply with the format or is required and is not present."),
            @ApiResponse(responseCode = "500", description = "Internal server error.")
    })
    public ResponseEntity<String> deleteRoomBooking (@PathVariable Long id){

        try{
            RoomBooking deleteBooking = roomBookingService.deleteRoomBooking(id);
            if (deleteBooking != null) {
                return ResponseEntity.ok("RoomBooking deleted successfully.");
            } else {
                return new ResponseEntity<>("RoomBooking not found.", HttpStatus.NOT_FOUND);
            }
        } catch(Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error to delete. " + e.getMessage());
        }
    }


}
