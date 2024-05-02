package com.hackaboss.PruebaTecnica4.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String roomCode;
    private String roomType;
    private double roomPrice;
    private Boolean isBooked;
    private Boolean status;

    @ManyToOne
    @JoinColumn(name="hotel_id")
    @JsonBackReference
    private Hotel hotel;

    @OneToMany(mappedBy = "room", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<RoomBooking> roomBookings = new ArrayList<>();

}
