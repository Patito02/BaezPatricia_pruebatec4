package com.hackaboss.PruebaTecnica4.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class FlightBooking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDate date;
    private String origin;
    private String destination;
    private String seatType;
    private Integer peopleQ;
    private Double totalPrice;
    private Boolean status;

    //@JsonIgnore
    @ManyToMany
    @JoinTable(
            name = "person_flight_booking",
            joinColumns = @JoinColumn(name = "flight_booking_id"),
            inverseJoinColumns = @JoinColumn(name = "person_id"))
    private List<Person> passengers = new ArrayList<>();

    //@JsonIgnore //
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="flight_id")
    @JsonBackReference
    private Flight flight;

}
