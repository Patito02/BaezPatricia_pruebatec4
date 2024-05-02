package com.hackaboss.PruebaTecnica4.controller;

import com.hackaboss.PruebaTecnica4.dto.RoomDTO;
import com.hackaboss.PruebaTecnica4.exception.RoomBookingException;
import com.hackaboss.PruebaTecnica4.model.Room;
import com.hackaboss.PruebaTecnica4.service.IRoomService;
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

@Tag(name = "Room")
@RestController
@RequestMapping("/agency/rooms")
public class RoomController {

    @Autowired
    private IRoomService roomService;

    @PostMapping("/new")
    @Operation(summary = "Agregar una habitación")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "The operation was executed successfully."),
            @ApiResponse(responseCode = "400", description = "Some parameter does not comply with the format or is required and is not present."),
            @ApiResponse(responseCode = "500", description = "Internal server error.")
    })
    public ResponseEntity<?> createRoom (@RequestBody Room room){
        try {
            Room addRoom = roomService.saveRoom(room);
            if(addRoom !=null) {
                return new ResponseEntity<>("Room created successfully.", HttpStatus.CREATED);
            } else {
                return new ResponseEntity<>("Room already registered.", HttpStatus.BAD_REQUEST);
            }
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error to create. " + e.getMessage());
        }
    }
    @GetMapping("/all")
    @Operation(summary = "Ver todas las habitaciones")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "The operation was executed successfully."),
            @ApiResponse(responseCode = "400", description = "Some parameter does not comply with the format or is required and is not present."),
            @ApiResponse(responseCode = "500", description = "Internal server error.")
    })
    public List<RoomDTO> getAllRooms(){
        return roomService.getAllRooms();
    }


    @GetMapping("/{id}")
    @Operation(summary = "Buscar una habitación por su id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "The operation was executed successfully."),
            @ApiResponse(responseCode = "400", description = "Some parameter does not comply with the format or is required and is not present."),
            @ApiResponse(responseCode = "500", description = "Internal server error.")
    })
    public ResponseEntity<?> findRoomById(@PathVariable Long id){
        try {
            RoomDTO findRoom =  roomService.findRoomById(id);
            if (findRoom != null) {
                return new ResponseEntity<>(findRoom, HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Room not found.",HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping
    @Operation(summary = "Obtener una habitación por destino, fecha de entrada y fecha de salida")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "The operation was executed successfully."),
            @ApiResponse(responseCode = "400", description = "Some parameter does not comply with the format or is required and is not present."),
            @ApiResponse(responseCode = "500", description = "Internal server error.")
    })
    public ResponseEntity<?> getRoomByDestinationAndDate (
            @RequestParam  LocalDate dateFrom,
            @RequestParam  LocalDate dateTo,
            @RequestParam String destination){

        try {
            List<RoomDTO> findListRoom = roomService.getRoomByDestinationAndDate(dateFrom, dateTo, destination);
            if (!findListRoom.isEmpty()) {
                return new ResponseEntity<>(findListRoom, HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Room not found for date and destination selected.",HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
    @PutMapping("/edit/{id}")
    @Operation(summary = "Editar una habitación",
            description = "Este método permite buscar una habitación por su id y editarla.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "The operation was executed successfully."),
            @ApiResponse(responseCode = "400", description = "Some parameter does not comply with the format or is required and is not present."),
            @ApiResponse(responseCode = "500", description = "Internal server error.")
    })
    public ResponseEntity<String> editRoom(@PathVariable Long id, @RequestBody Room room){

        try {
            Room editRoom = roomService.editRoom(id,room);
            if (editRoom != null) {
                return ResponseEntity.ok("Room edited successfully.");
            } else {
                return new ResponseEntity<>("Room not found.", HttpStatus.NOT_FOUND);
            }
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error to edit. " + e.getMessage());
        }

    }
    @DeleteMapping("/delete/{id}")
    @Operation(summary = "Eliminar una habitación por id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "The operation was executed successfully."),
            @ApiResponse(responseCode = "400", description = "Some parameter does not comply with the format or is required and is not present."),
            @ApiResponse(responseCode = "500", description = "Internal server error.")
    })
    public ResponseEntity<String> deleteRoom(@PathVariable Long id){

        try {
            Room deleteRoom = roomService.deleteRoom(id);
            if (deleteRoom != null) {
                return ResponseEntity.ok("Room deleted successfully.");
            } else {
                return new ResponseEntity<>("Room not found.", HttpStatus.NOT_FOUND);
            }
        } catch (RoomBookingException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error to delete. " + e.getMessage());
        }
    }



}
