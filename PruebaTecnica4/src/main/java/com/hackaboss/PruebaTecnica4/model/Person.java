package com.hackaboss.PruebaTecnica4.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
public class Person {

    @Id
    private String dni;
    private String name;
    private String lastName;
    private String email;
    private Boolean status;

    @JsonIgnore
    @ManyToMany(mappedBy = "hosts")
    private List<RoomBooking> roomBookings = new ArrayList<>();

    @ManyToMany(mappedBy = "passengers")
    private List<FlightBooking> flightBookings = new ArrayList<>();


}
