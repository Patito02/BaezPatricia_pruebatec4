package com.hackaboss.PruebaTecnica4.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Flight {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String flightCode;
    private LocalDate date;
    private String origin;
    private String destination;
    private Integer seatsQ;
    private Integer seatsAvailable;
    private String seatType;
    private Double flightPrice;
    private Boolean status;
    private Boolean isComplete;

    @OneToMany(mappedBy = "flight", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<FlightBooking> flightBookings = new ArrayList<>();
}
