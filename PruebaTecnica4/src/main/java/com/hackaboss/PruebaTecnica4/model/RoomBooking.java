package com.hackaboss.PruebaTecnica4.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor

@Entity
public class RoomBooking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String roomType;
    private Integer peopleQ;
    private LocalDate dateFrom;
    private LocalDate dateTo;
    private int nights;
    private double totalPrice;
    private Boolean status;
    private String place;

    @ManyToMany
    @JoinTable(
            name = "person_roomBooking",
            joinColumns = @JoinColumn(name = "roomBooking_id"),
            inverseJoinColumns = @JoinColumn(name = "person_id"))
    private List<Person> hosts = new ArrayList<>();

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="room_id")
    @JsonBackReference
    private Room room;

}
